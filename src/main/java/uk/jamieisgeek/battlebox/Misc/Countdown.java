package uk.jamieisgeek.battlebox.Misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import uk.jamieisgeek.battlebox.BattleBox;

public class Countdown {
    private static final BattleBox plugin = BattleBox.getPlugin();
    public void beginCountdown() {
        final int[] timer = {30};
        new BukkitRunnable() {
            @Override
            public void run() {
                timer[0]--;

                if(timer[0] == 0) {
                    this.cancel();
                    plugin.getGameManager().beginGame();
                    plugin.getQueueManager().getQueue().forEach((uuid, name) -> Bukkit.getPlayer(uuid).sendMessage(ChatColor.AQUA + String.valueOf(ChatColor.BOLD) + "The game is starting!"));
                    return;
                }

                plugin.getQueueManager().getQueue().forEach((uuid, name) -> Bukkit.getPlayer(uuid).sendMessage(ChatColor.AQUA + String.valueOf(ChatColor.BOLD) + "The game will start in " + timer[0] + " seconds!"));
            }
        }.runTaskTimer(BattleBox.getPlugin(), 0L,  20L);
    }
}
