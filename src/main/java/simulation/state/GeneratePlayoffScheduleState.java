package simulation.state;

public class GeneratePlayoffScheduleState implements  ISimulateState{
    @Override
    public ISimulateState action() {
        System.out.println("Generating Play off Schedule");

        return exit();
    }

    private ISimulateState exit() {
        return new TraningState();
    }
}
