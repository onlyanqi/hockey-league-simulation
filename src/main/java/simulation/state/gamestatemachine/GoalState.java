package simulation.state.gamestatemachine;

import simulation.model.GameSimulation;
import simulation.model.Shift;
import simulation.state.GameContext;
import simulation.state.IGameState;

import java.util.HashMap;
import java.util.Random;

public class GoalState implements IGameState {

    Random rand;
    GameContext gameContext;
    GameSimulation gameSimulation;
    Shift offensive;
    Shift defensive;
    boolean goal;

    public GoalState(GameContext gameContext) {
        rand = new Random();
        this.gameContext = gameContext;
        gameSimulation = gameContext.getGameSimulation();
        offensive = gameContext.getOffensive();
        defensive = gameContext.getDefensive();
    }

    @Override
    public IGameState process() throws Exception {
        if(offensive.getGoalie().getSaving() > defensive.getGoalie().getSaving()){
            goal = true;
        }else{
            goal = false;
        }
        if(rand.nextDouble() < 0.4){
            reverseGoal();
        }

        if(goal && (rand.nextDouble() < 0.21)){
            goal = true;
        }else{
            goal = false;
        }
        updateSimulationStats();
        return null;
    }

    boolean reverseGoal(){
        return !goal;
    }

    private void updateSimulationStats() {
        HashMap<String,Integer> goals = gameSimulation.getGoals();
        HashMap<String,Integer> shots = gameSimulation.getShots();
        HashMap<String,Integer> saves = gameSimulation.getSaves();
        shots.put(offensive.getTeamName(), shots.get(offensive.getTeamName()) + 1);
        if(goal){
            goals.put(offensive.getTeamName(), goals.get(offensive.getTeamName()) + 1);
        }else{
            saves.put(defensive.getTeamName(), saves.get(defensive.getTeamName()) + 1);
        }
    }
}
