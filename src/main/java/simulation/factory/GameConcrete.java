package simulation.factory;


import simulation.model.Game;
import simulation.model.IGame;

public class GameConcrete implements IGameFactory{

    @Override
    public IGame newGame() {
        return new Game();
    }
}
