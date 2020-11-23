package simulation.factory;

import db.dao.PlayerDao;
import db.data.IPlayerDao;
import simulation.model.IPlayer;
import simulation.model.Player;

public class PlayerConcrete implements IPlayerFactory {

    public IPlayer newPlayer() {
        return new Player();
    }

    public IPlayerDao newPlayerDao(){
        return new PlayerDao();
    }

    public IPlayer newPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception {
        return new Player(id, playerDao);
    }
}
