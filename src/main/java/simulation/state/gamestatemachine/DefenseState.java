package simulation.state.gamestatemachine;

import simulation.model.IPlayer;
import simulation.model.Shift;
import simulation.state.GameContext;
import simulation.state.IGameState;

import java.util.Random;

public class DefenseState implements IGameState {

    Random rand;
    GameContext gameContext;
    Shift offensive;
    Shift defensive;
    boolean defend;

    public DefenseState(GameContext gameContext) {
        this.gameContext = gameContext;
        offensive = gameContext.getOffensive();
        defensive = gameContext.getDefensive();
        this.rand = new Random();
    }

    @Override
    public IGameState process() throws Exception{
        if(offensive.getTeamShiftDefenseTotal() > defensive.getTeamShiftDefenseTotal()){
            defend = false;
        }else{
            defend = true;
        }
        if(rand.nextDouble() < 0.4){
            reverseDefending();
        }
        if(defend && (rand.nextDouble() < 0.25)){
            defend = true;
        }else{
            defend = false;
        }
        return next();
    }

    public IGameState next(){
        if(defend){
            if(rand.nextDouble() < 0.65){
                return new PenaltyState(gameContext);
            }
        }else{
            return new GoalState(gameContext);
        }
        return null;
    }

    private boolean reverseDefending() {
        return !defend;
    }
}
