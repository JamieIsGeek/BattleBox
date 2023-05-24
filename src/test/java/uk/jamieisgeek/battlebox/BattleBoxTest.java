package uk.jamieisgeek.battlebox;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.*;
import uk.jamieisgeek.battlebox.Game.State.State;

class BattleBoxTest {
    private ServerMock server;
    private BattleBox plugin;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(BattleBox.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void setState() {
        plugin.getGameState().setState(State.IN_PROGRESS);
        assert plugin.getGameState().isState(State.IN_PROGRESS);
    }
}