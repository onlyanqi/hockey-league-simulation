package simulation.state;

public class SimulateGameState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Simulating game, winner is ...");
        return exit();
    }

    private ISimulateState exit() {
        return new InjuryCheckState();
    }
}
