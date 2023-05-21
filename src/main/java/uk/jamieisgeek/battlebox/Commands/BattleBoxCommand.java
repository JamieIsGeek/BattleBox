package uk.jamieisgeek.battlebox.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.ScoreboardAssistant;

public class BattleBoxCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        BattleBox.getPlugin().getDatabase().getDatabaseManager().updatePlayerScore(player.getName(), 69);

        if(args.length == 1) {
            switch(args[0].toLowerCase()) {
                case "test" -> {
                    int playerScore = BattleBox.getPlugin().getDatabase().getDatabaseManager().getPlayerScore(player.getName());
                    ScoreboardAssistant scoreboardAssistant = new ScoreboardAssistant(5, "&6&lBattleBox");
                    scoreboardAssistant.setLine(player, 0, "&c&lTeam: &e&lRed");
                    scoreboardAssistant.setLine(player, 1, "&c&lKills: &e&l0");
                    scoreboardAssistant.setLine(player, 2, "&c&lScore: &e&l" + playerScore);

                    scoreboardAssistant.displayForPlayer(player);
                    player.sendMessage();
                }

                case "reset" -> {
                    new ScoreboardAssistant(0, "&6&lBattleBox").removePlayerFromScoreboard(player);
                }
            }
        }
        return true;
    }
}
