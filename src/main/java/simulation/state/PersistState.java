package simulation.state;

public class PersistState implements ISimulateState {

    private HockeyContext hockeyContext;

    public PersistState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
    }

    @Override
    public ISimulateState action() {
        System.out.println("Saving league to DB");
        return exit();
    }

    private ISimulateState exit() {
        Boolean stanleyCupDetermined = true;
        if (stanleyCupDetermined) {
            return null;
        } else {
            return new AdvanceTimeState(hockeyContext);
        }
    }
}
