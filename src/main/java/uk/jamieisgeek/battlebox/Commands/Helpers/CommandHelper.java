package uk.jamieisgeek.battlebox.Commands.Helpers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.ScoreboardAssistant;

public class CommandHelper {
    private final BattleBox plugin;
    private final ScoreboardAssistant scoreboardAssistant;
    public CommandHelper(BattleBox plugin) {
        this.plugin = plugin;
        this.scoreboardAssistant = new ScoreboardAssistant(3, "&b&lBattleBox");
    }

    public void ShowQueueScoreboard() {
        scoreboardAssistant.setUniversalLine(0, "&e&lWaiting for players...");
        scoreboardAssistant.setUniversalLine(2, "&e&lPlayers: &f" + plugin.getQueueManager().getQueue().size() + "/8");

        plugin.getQueueManager().getQueue().forEach((uuid, name) -> {
            scoreboardAssistant.displayForPlayer(plugin.getServer().getPlayer(uuid));
        });
    }

    public void RemoveScoreboard(Player player) {
        scoreboardAssistant.removePlayerFromScoreboard(player);

        this.ShowQueueScoreboard();
    }

    public void AlertQueueAction(Player player, String action) {
        switch (action) {
            case "join" -> plugin.getQueueManager().getQueue().forEach((uuid, name) -> {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getName() + " has joined the queue! (" + plugin.getQueueManager().getQueue().size() + "/8)");
            });

            case "leave" -> plugin.getQueueManager().getQueue().forEach((uuid, name) -> {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + player.getName() + " has left the queue! (" + plugin.getQueueManager().getQueue().size() + "/8)");
            });
        }
    }
}
