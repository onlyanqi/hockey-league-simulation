package simulation.state.gamestatemachine;

public abstract class GameState {

    public abstract GameState process() throws Exception;

    boolean shouldContinue() {
        return true;
    }
}
