package simulation.state.gamestatemachine;

import simulation.model.*;
import simulation.state.HockeyContext;
import java.util.Random;

public class DefenseState extends GameState {

    Random rand;
    GameContext gameContext;
    Shift offensive;
    Shift defensive;
    ISimulate simulateConfig;
    boolean defend;

    public DefenseState(GameContext gameContext) {
        simulateConfig  = HockeyContext.getInstance().getUser().getLeague().getGamePlayConfig().getSimulate();
        this.gameContext = gameContext;
        offensive = gameContext.getOffensive();
        defensive = gameContext.getDefensive();
        this.rand = new Random();
    }

    public GameState process() throws Exception{
        if(offensive.getTeamShiftDefenseTotal() > defensive.getTeamShiftDefenseTotal()){
            defend = false;
        }else{
            defend = true;
        }
        if(rand.nextDouble() < simulateConfig.getUpset()){
            reverseDefending();
        }
        if(defend && (rand.nextDouble() < simulateConfig.getDefendChance())){
            defend = true;
        }else{
            defend = false;
        }
        return next();
    }

    public GameState next(){
        if(defend){
            if(rand.nextDouble() < simulateConfig.getPenaltyChance()){
                return new PenaltyState(gameContext);
            }
        }else{
            return new GoalState(gameContext);
        }
        return new FinalState();
    }

    private boolean reverseDefending() {
        return !defend;
    }
}
