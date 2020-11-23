package simulation.factory;

import db.dao.PlayerDao;
import db.data.IPlayerDao;
import simulation.model.IPlayer;
import simulation.model.Player;

public interface IPlayerFactory {

    IPlayer newPlayer();

    IPlayerDao newPlayerDao();

    IPlayer newPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception;

}
