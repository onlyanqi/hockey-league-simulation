package simulation.state;

public class TraningState implements ISimulateState {
    @Override
    public ISimulateState action() {
        System.out.println("Training Players and Team!");
        return exit();
    }

    private ISimulateState exit() {
        Boolean unplannedGamesScheduled = true;
        Boolean tradeDeadlinePassed = true;

        if(unplannedGamesScheduled){
            return new SimulateGameState();
        }else{
            if(tradeDeadlinePassed){
                return new AgingState();
            }else{
                return new ExecuteTradeState();
            }
        }

    }
}
