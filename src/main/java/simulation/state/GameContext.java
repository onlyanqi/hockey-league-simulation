package simulation.state;

import simulation.model.GameSimulation;
import simulation.model.Shift;
import simulation.state.gamestatemachine.GoalState;
import simulation.state.gamestatemachine.ShootingState;

public class GameContext {

    Shift offensive;
    Shift defensive;
    GameSimulation gameSimulation;

    public GameContext(GameSimulation gameSimulation) {
        this.offensive = new Shift();
        this.defensive = new Shift();
        this.gameSimulation = gameSimulation;
    }

    public void start() throws Exception {

        IGameState iGameState = new ShootingState(this);
        do{
            iGameState = iGameState.process();
        }while(iGameState!=null);
    }

    public Shift getOffensive() {
        return offensive;
    }

    public void setOffensive(Shift offensive) {
        this.offensive = offensive;
    }

    public Shift getDefensive() {
        return defensive;
    }

    public void setDefensive(Shift defensive) {
        this.defensive = defensive;
    }

    public GameSimulation getGameSimulation() {
        return gameSimulation;
    }

    public void setGameSimulation(GameSimulation gameSimulation) {
        this.gameSimulation = gameSimulation;
    }
}
