package simulation.factory;

import db.dao.GameDao;
import db.data.IGameFactory;

public class GameConcrete {
    public IGameFactory newAddGamesFactory() {
        return new GameDao();
    }
}
