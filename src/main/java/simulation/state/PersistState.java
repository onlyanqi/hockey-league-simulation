package simulation.state;

public class PersistState implements ISimulateState{
    @Override
    public ISimulateState action() {
        System.out.println("Saving league to DB");
        return exit();
    }

    private ISimulateState exit() {
        Boolean stanleyCupDetermined = true;
        if(stanleyCupDetermined){
            return null;
        }else{
            return new AdvanceTimeState();
        }
    }
}
