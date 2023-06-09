package uk.jamieisgeek.battlebox.Misc;

import org.bukkit.entity.Player;
import uk.jamieisgeek.battlebox.BattleBox;

public class ScoreboardHelper {
    private final BattleBox plugin;
    private final ScoreboardAssistant scoreboardAssistant;
    private static ScoreboardHelper scoreboardHelper;

    public ScoreboardHelper(BattleBox plugin) {
        this.plugin = plugin;
        this.scoreboardAssistant = new ScoreboardAssistant(3, "&b&lBattleBox");
        scoreboardHelper = this;
    }

    public void ShowQueueScoreboard() {
        scoreboardAssistant.setUniversalLine(0, "&e&lWaiting for players...");
        scoreboardAssistant.setUniversalLine(2, "&e&lPlayers: &f" + plugin.getQueueManager().getQueue().size() + "/4");

        plugin.getQueueManager().getQueue().forEach((uuid, name) -> {
            scoreboardAssistant.displayForPlayer(plugin.getServer().getPlayer(uuid));
        });
    }

    public void RemoveScoreboard(Player player) {
        scoreboardAssistant.removePlayerFromScoreboard(player);

        this.ShowQueueScoreboard();
    }

    public void deleteScoreboard(Player player) {
        scoreboardAssistant.removePlayerFromScoreboard(player);
    }

    public static ScoreboardHelper getScoreboardHelper() {
        return scoreboardHelper;
    }
}
