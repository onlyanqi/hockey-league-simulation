package simulation.state;

public class AgingState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Aging players!");
        return exit();
    }

    private ISimulateState exit() {
        boolean stanleyCupWinnerDetermined = true;
        if(stanleyCupWinnerDetermined){
            return new AdvanceNextSeasonState();
        }else{
            return new PersistState();
        }
    }
}
