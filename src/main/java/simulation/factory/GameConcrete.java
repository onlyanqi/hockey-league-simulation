package simulation.factory;

import db.dao.EventDao;
import db.dao.GameDao;
import db.data.IEventFactory;
import db.data.IGameFactory;
import simulation.model.Game;
import simulation.model.NHLEvents;

public class GameConcrete {
    public Game newGame() {
        return new Game();
    }

    public IGameFactory newLoadGame() {
        return new GameDao();
    }

    public IGameFactory newAddGamesFactory() {
        return new GameDao();
    }
}
