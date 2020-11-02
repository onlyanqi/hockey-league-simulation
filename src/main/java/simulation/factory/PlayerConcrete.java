package simulation.factory;

import db.dao.PlayerDao;
import db.data.IPlayerFactory;
import simulation.model.Player;

public class PlayerConcrete {

    public Player newPlayer() {
        return new Player();
    }

    public IPlayerFactory newPlayerFactory() {
        return new PlayerDao();
    }

}
