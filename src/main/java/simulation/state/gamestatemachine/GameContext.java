package simulation.state.gamestatemachine;

import simulation.model.GameSimulation;
import simulation.model.IShift;
import simulation.model.Shift;

public class GameContext {

    IShift offensive;
    IShift defensive;
    GameSimulation gameSimulation;
    GameState gameState;


    public GameContext(GameSimulation gameSimulation) {
        this.offensive = new Shift();
        this.defensive = new Shift();
        this.gameSimulation = gameSimulation;
    }

    public void start() throws Exception {

        GameState gameState = new ShootingState(this);
        while(gameState.shouldContinue()){
            gameState = gameState.process();
        }
    }

    public IShift getOffensive() {
        return offensive;
    }

    public void setOffensive(IShift offensive) {
        this.offensive = offensive;
    }

    public IShift getDefensive() {
        return defensive;
    }

    public void setDefensive(IShift defensive) {
        this.defensive = defensive;
    }

    public GameSimulation getGameSimulation() {
        return gameSimulation;
    }

    public void setGameSimulation(GameSimulation gameSimulation) {
        this.gameSimulation = gameSimulation;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
