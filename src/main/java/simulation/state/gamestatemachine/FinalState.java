package simulation.state.gamestatemachine;

public class FinalState extends GameState{

    @Override
    public GameState process() throws Exception {
        return null;
    }

    @Override
    boolean shouldContinue() {
        return false;
    }
}
