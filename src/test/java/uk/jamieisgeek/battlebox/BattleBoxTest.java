package uk.jamieisgeek.battlebox;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BattleBoxTest {

    private ServerMock mockServer;
    private BattleBox plugin;
    @BeforeEach
    void setUp() {
        mockServer = MockBukkit.mock();
        plugin = MockBukkit.load(BattleBox.class);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void getConfigHandler() {
        assert plugin.getConfigHandler() != null;
    }

    @Test
    void getPlugin() {
        assert BattleBox.getPlugin() != null;
    }

    @Test
    void getQueueManager() {
        assert plugin.getQueueManager() != null;
    }

    @Test
    void getGameState() {
        assert plugin.getGameState() != null;
    }

    @Test
    void getGameManager() {
        assert plugin.getGameManager() != null;
    }

    @Test
    void getKitManager() {
        assert plugin.getKitManager() != null;
    }

    @Test
    void getGuiManager() {
        assert plugin.getGuiManager() != null;
    }
}