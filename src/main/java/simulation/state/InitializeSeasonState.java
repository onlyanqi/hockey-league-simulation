package simulation.state;

public class InitializeSeasonState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Initializing Season 2020");
        return exit();
    }

    private ISimulateState exit() {
        return new AdvanceTimeState();
    }
}
