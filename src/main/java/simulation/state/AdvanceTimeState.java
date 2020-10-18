package simulation.state;

public class AdvanceTimeState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Advanced day!");
        return exit();
    }

    private ISimulateState exit() {
        boolean endOfSeason = true;
        if(endOfSeason){
            return new GeneratePlayoffScheduleState();
        }else{
            return new TraningState();
        }
    }
}
