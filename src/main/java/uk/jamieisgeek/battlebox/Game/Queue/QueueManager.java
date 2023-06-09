package uk.jamieisgeek.battlebox.Game.Queue;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import uk.jamieisgeek.battlebox.BattleBox;

import java.util.HashMap;
import java.util.UUID;

public class QueueManager {
    private final HashMap<UUID, String> queue;
    private final BattleBox plugin;

    public QueueManager(BattleBox plugin) {
        this.queue = new HashMap<>();
        this.plugin = plugin;
    }

    public void add(UUID uuid, String name) {
        queue.put(uuid, name);
        plugin.getLogger().info("Added " + uuid + " to the queue");

        if(isFull()) {
            queue.forEach((uuid1, name1) -> plugin.getServer().getPlayer(uuid1).sendMessage(plugin.getConfigHandler().getFromMessages("game.starting")));
            plugin.getGameManager().Setup();
        }
    }

    public void remove(UUID uuid) {
        queue.remove(uuid);

        AlertQueueAction(Bukkit.getPlayer(uuid), "leave");

        if(Bukkit.getPlayer(uuid) != null) {
            Bukkit.getPlayer(uuid).sendMessage(plugin.getConfigHandler().getFromMessages("queue.leave"));
        }


    }

    public void AlertQueueAction(Player player, String action) {
        switch (action) {
            case "join" -> getQueue().forEach((uuid, name) -> plugin.getServer().getPlayer(uuid).sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getName() + " has joined the queue! (" + plugin.getQueueManager().getQueue().size() + "/8)"));

            case "leave" -> getQueue().forEach((uuid, name) -> plugin.getServer().getPlayer(uuid).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + player.getName() + " has left the queue! (" + plugin.getQueueManager().getQueue().size() + "/8)"));
        }
    }

    public boolean contains(UUID uuid) {
        return queue.containsKey(uuid);
    }

    public boolean isFull() {
        return queue.size() >= 4;
    }

    public HashMap<UUID, String> getQueue() {
        return queue;
    }
}
