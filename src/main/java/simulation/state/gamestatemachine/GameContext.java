package simulation.state.gamestatemachine;

import simulation.model.IGameSimulation;
import simulation.model.IShift;
import simulation.state.HockeyContext;

public class GameContext {

    IShift offensive;
    IShift defensive;
    IGameSimulation gameSimulation;
    GameState gameState;


    public GameContext(IGameSimulation gameSimulation) {
        this.offensive = HockeyContext.getInstance().getModelFactory().newShift();
        this.defensive = HockeyContext.getInstance().getModelFactory().newShift();
        this.gameSimulation = gameSimulation;
    }

    public void start() throws Exception {

        GameState gameState = new ShootingState(this);
        while (gameState.shouldContinue()) {
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

    public IGameSimulation getGameSimulation() {
        return gameSimulation;
    }

    public void setGameSimulation(IGameSimulation gameSimulation) {
        this.gameSimulation = gameSimulation;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
