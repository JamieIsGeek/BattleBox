package uk.jamieisgeek.battlebox.Listeners.General;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.jamieisgeek.battlebox.BattleBox;
import uk.jamieisgeek.battlebox.Game.State.State;

import javax.swing.*;

public class ConnectionListener implements Listener {
    private final BattleBox plugin;
    public ConnectionListener(BattleBox plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(!plugin.getQueueManager().getQueue().containsKey(event.getPlayer().getUniqueId())) {
            return;
        }
        if(plugin.getGameState().isState(State.IN_PROGRESS)) {
            plugin.getGameManager().killPlayer(event.getPlayer(), "disconnection");
        } else if (plugin.getGameState().isState(State.LOBBY)) {
            plugin.getQueueManager().remove(event.getPlayer().getUniqueId());
        }
    }
}
