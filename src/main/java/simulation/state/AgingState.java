package simulation.state;

public class AgingState implements ISimulateState {

    private HockeyContext hockeyContext;

    public AgingState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
    }

    @Override
    public ISimulateState action() {
        System.out.println("Aging players!");
        return exit();
    }

    private ISimulateState exit() {
        boolean stanleyCupWinnerDetermined = true;
        if(stanleyCupWinnerDetermined){
            return new AdvanceNextSeasonState(hockeyContext);
        }else{
            return new PersistState(hockeyContext);
        }
    }
}
