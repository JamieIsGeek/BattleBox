package uk.jamieisgeek.battlebox.Game.State;

public class GameState {
    private State state;

    public GameState() {
        this.state = State.LOBBY;
    }

    public boolean isState(State state) {
        return this.state == state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}