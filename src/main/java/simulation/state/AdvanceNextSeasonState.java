package simulation.state;

public class AdvanceNextSeasonState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Winner is announced! Advancing to next season. ");
        return exit();
    }

    private ISimulateState exit() {
        return new PersistState();
    }
}
