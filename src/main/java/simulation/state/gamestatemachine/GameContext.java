package simulation.state.gamestatemachine;

import simulation.model.GameSimulation;
import simulation.model.Shift;
import simulation.state.gamestatemachine.GameState;
import simulation.state.gamestatemachine.ShootingState;

public class GameContext {

    Shift offensive;
    Shift defensive;
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
