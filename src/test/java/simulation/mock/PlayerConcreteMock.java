package simulation.mock;

import db.dao.PlayerDao;
import db.data.IPlayerDao;
import simulation.factory.IPlayerFactory;
import simulation.model.IPlayer;
import simulation.model.Player;

public class PlayerConcreteMock implements IPlayerFactory {

    public IPlayer newPlayer() {
        return new Player();
    }

    public IPlayerDao newPlayerDao(){
        return new PlayerMock();
    }

    public IPlayer newPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception {
        return new Player(id, playerDao);
    }

}
