package uk.jamieisgeek.battlebox.Game.Queue;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.jamieisgeek.battlebox.BattleBox;

public class QueueManagerTest {
    private static ServerMock server;
    private static BattleBox plugin;
    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(BattleBox.class);
    }

    @Test
    public void add() {
        PlayerMock player = server.addPlayer();
        plugin.getQueueManager().add(player.getUniqueId(), player.getName());
        assert plugin.getQueueManager().contains(player.getUniqueId());
    }

    @Test
    public void remove() {
        PlayerMock player = server.addPlayer();
        plugin.getQueueManager().add(player.getUniqueId(), player.getName());
        plugin.getQueueManager().remove(player.getUniqueId());
        assert !plugin.getQueueManager().contains(player.getUniqueId());
    }

    @Test
    public void contains() {
        PlayerMock player = server.addPlayer();
        plugin.getQueueManager().add(player.getUniqueId(), player.getName());
        assert plugin.getQueueManager().contains(player.getUniqueId());
    }

    @Test
    public void isFull() {
        for(int i = 0; i < 8; i++) {
            PlayerMock player = server.addPlayer();
            plugin.getQueueManager().add(player.getUniqueId(), player.getName());
        }
        assert plugin.getQueueManager().isFull();
    }

    @Test
    public void getQueue() {
        PlayerMock player = server.addPlayer();
        plugin.getQueueManager().add(player.getUniqueId(), player.getName());
        assert plugin.getQueueManager().getQueue().containsKey(player.getUniqueId());
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }
}