package uk.jamieisgeek.battlebox.Game.Queue;

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
        }
    }

    public void remove(UUID uuid) {
        queue.remove(uuid);
        plugin.getLogger().info("Removed " + uuid + " from the queue");
    }

    public boolean contains(UUID uuid) {
        return queue.containsKey(uuid);
    }

    public boolean isFull() {
        return queue.size() >= 8;
    }

    public HashMap<UUID, String> getQueue() {
        return queue;
    }
}
