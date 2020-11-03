package simulation.state;

import simulation.factory.ValidationConcrete;
import validator.IValidation;

public class InternalState implements IHockeyState {

    private HockeyContext hockeyContext;
    private ISimulateState simulateState;
    private IValidation iValidation;

    public InternalState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        ValidationConcrete validationConcrete = new ValidationConcrete();
        iValidation = validationConcrete.newValidation();
    }

    @Override
    public void entry() {
        simulateState = new InitializeSeasonState(hockeyContext);
        simulateState.action();
    }

    @Override
    public void process() {
        do {
            simulateState = simulateState.action();
        } while (iValidation.isNotNull(simulateState));
    }

    @Override
    public IHockeyState exit() {
        System.out.println("Exiting App!");
        return null;
    }
}