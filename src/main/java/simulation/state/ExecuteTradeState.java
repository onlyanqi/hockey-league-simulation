package simulation.state;

public class ExecuteTradeState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Trading!");
        return exit();
    }

    private ISimulateState exit() {
        return new AgingState();
    }
}
