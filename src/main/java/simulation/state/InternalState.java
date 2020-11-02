package simulation.state;

import validator.Validation;

public class InternalState implements IHockeyState {

    private HockeyContext hockeyContext;
    private ISimulateState simulateState;

    public InternalState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() {
        simulateState = new InitializeSeasonState(hockeyContext);
        simulateState.action();
    }

    @Override
    public void process() {
        Validation validation = new Validation();
        do {
            simulateState = simulateState.action();
        } while (validation.isNotNull(simulateState));
    }

    @Override
    public IHockeyState exit() {
        System.out.println("Exiting App!");
        return null;
    }
}