package uk.jamieisgeek.battlebox.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.Game.State.State;
import uk.jamieisgeek.battlebox.ScoreboardAssistant;

public class BattleBoxCommand implements CommandExecutor {
    private final BattleBox plugin;
    public BattleBoxCommand(BattleBox plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        BattleBox.getPlugin().getDatabase().getDatabaseManager().updatePlayerScore(player.getName(), 69);

        if(args.length == 1) {
            switch(args[0].toLowerCase()) {
                case "join" -> {
                    if(plugin.getQueueManager().contains(player.getUniqueId())) {
                        player.sendMessage(plugin.getConfigHandler().getFromMessages("error.already_in_queue"));
                        return true;
                    }

                    if(plugin.getGameState().isState(State.ENDING) || plugin.getGameState().isState(State.IN_PROGRESS)) {
                        player.sendMessage(plugin.getConfigHandler().getFromMessages("error.game_already_started"));
                        return true;
                    }

                    if(plugin.getQueueManager().isFull()) {
                        player.sendMessage(plugin.getConfigHandler().getFromMessages("error.queue_full"));
                        return true;
                    }

                    plugin.getQueueManager().add(player.getUniqueId(), player.getName());
                    player.sendMessage(plugin.getConfigHandler().getFromMessages("queue.joined"));
                }

                case "leave" -> {
                    if(!plugin.getQueueManager().contains(player.getUniqueId())) {
                        player.sendMessage(plugin.getConfigHandler().getFromMessages("error.not_in_queue"));
                        return true;
                    }

                    if(plugin.getGameState().isState(State.ENDING) || plugin.getGameState().isState(State.IN_PROGRESS)) {
                        player.sendMessage(plugin.getConfigHandler().getFromMessages("error.game_already_started"));
                        return true;
                    }

                    plugin.getQueueManager().remove(player.getUniqueId());
                    player.sendMessage(plugin.getConfigHandler().getFromMessages("queue.left"));
                }
            }
        }
        return true;
    }
}
