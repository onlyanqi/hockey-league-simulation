package simulation.state;

import presentation.ConsoleOutput;

public class InternalState implements IHockeyState {

    private IHockeyContext hockeyContext;
    private ISimulateState simulateState;

    public InternalState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() throws Exception {
        simulateState = new InitializeSeasonState(hockeyContext);
        simulateState.action();
    }

    @Override
    public void process() throws Exception {
        do {
            simulateState = simulateState.action();
        } while (simulateState instanceof ISimulateState);
    }

    @Override
    public IHockeyState exit() {
        ConsoleOutput.getInstance().printMsgToConsole("Exiting App!!!");
        return null;
    }
}