package simulation.state;

public class ExecuteTradeState implements ISimulateState {

    private HockeyContext hockeyContext;

    public ExecuteTradeState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
    }

    @Override
    public ISimulateState action() {
        System.out.println("Trading!");
        return exit();
    }

    private ISimulateState exit() {
        return new AgingState(hockeyContext);
    }
}
