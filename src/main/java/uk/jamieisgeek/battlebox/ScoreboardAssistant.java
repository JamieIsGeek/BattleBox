package uk.jamieisgeek.battlebox;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardAssistant {

    private final ScoreboardManager manager;
    private final Map<Player, Scoreboard> playerScoreboards;
    private final String title;
    private int numLines;
    private final Map<Integer, String> universalLines;

    /**
     * @author bumpjammy
     */

    public ScoreboardAssistant(int numLines, String title) {
        manager = Bukkit.getScoreboardManager();
        playerScoreboards = new HashMap<>();
        universalLines = new HashMap<>();
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.numLines = numLines;
    }

    private Scoreboard createPlayerScoreboard(Player player) {
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("nonflicker", "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        for (int i = 0; i < numLines; i++) {
            String lineEntry = ChatColor.RESET + String.valueOf(ChatColor.WHITE) + ChatColor.values()[i].toString() + ChatColor.RESET;
            String name = "line" + i;
            Team team = scoreboard.registerNewTeam(name);
            team.addEntry(lineEntry);
            objective.getScore(lineEntry).setScore(numLines - i - 1);

            if (universalLines.containsKey(i)) {
                team.setPrefix(universalLines.get(i));
            }
        }

        return scoreboard;
    }

    public void setLine(Player player, int lineNumber, String text) {
        if (lineNumber >= 0 && lineNumber < numLines) {
            Scoreboard scoreboard = playerScoreboards.get(player);

            if (scoreboard == null) {
                scoreboard = createPlayerScoreboard(player);
                playerScoreboards.put(player, scoreboard);
            }

            ChatColor lineColor = ChatColor.values()[lineNumber];
            String lineEntry = ChatColor.RESET + "" + ChatColor.WHITE + lineColor.toString() + ChatColor.RESET;
            Team team = scoreboard.getTeam("line" + lineNumber);
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', text));
        }
    }

    public void setUniversalLine(int lineNumber, String text) {
        if (lineNumber >= 0 && lineNumber < numLines) {
            universalLines.put(lineNumber, ChatColor.translateAlternateColorCodes('&', text));

            for (Scoreboard scoreboard : playerScoreboards.values()) {
                ChatColor lineColor = ChatColor.values()[lineNumber];
                String lineEntry = ChatColor.RESET + "" + ChatColor.WHITE + lineColor.toString() + ChatColor.RESET;
                Team team = scoreboard.getTeam("line" + lineNumber);
                team.setPrefix(universalLines.get(lineNumber));
            }
        }
    }

    public void displayForPlayer(Player player) {
        Scoreboard scoreboard = playerScoreboards.get(player);

        if (scoreboard == null) {
            scoreboard = createPlayerScoreboard(player);
            playerScoreboards.put(player, scoreboard);
        }

        player.setScoreboard(scoreboard);
    }

    public void removePlayerFromScoreboard(Player player) {
        player.setScoreboard(manager.getNewScoreboard());
        playerScoreboards.remove(player);
    }

    public void setNumLines(int lines) {
        if (lines > 0 && lines < 16) {
            // Update numLines with the new value
            this.numLines = lines;
            universalLines.clear();

            for (Player player : playerScoreboards.keySet()) {
                Scoreboard scoreboard = playerScoreboards.get(player);
                Objective objective = scoreboard.getObjective("nonflicker");
                objective.unregister();
                objective = scoreboard.registerNewObjective("nonflicker", "dummy", title);
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                for (int i = 0; i < lines; i++) {
                    String lineEntry = ChatColor.RESET + "" + ChatColor.WHITE + ChatColor.values()[i].toString() + ChatColor.RESET;
                    String name = "line" + i;
                    if (scoreboard.getTeam(name) != null) {
                        scoreboard.getTeam(name).unregister();
                    }
                    Team team = scoreboard.registerNewTeam(name);
                    team.addEntry(lineEntry);
                    objective.getScore(lineEntry).setScore(lines - i - 1);
                }

                for (Map.Entry<Integer, String> entry : universalLines.entrySet()) {
                    setUniversalLine(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void QueueScoreboard(Player player) {

    }

    public int getNumLines() {
        return numLines;
    }
}
