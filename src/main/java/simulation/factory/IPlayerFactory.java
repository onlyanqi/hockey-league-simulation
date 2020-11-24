package simulation.factory;

import simulation.dao.IPlayerDao;
import simulation.model.IPlayer;

public interface IPlayerFactory {

    IPlayer newPlayer();

    IPlayerDao newPlayerDao();

    IPlayer newPlayerWithIdDao(int id, IPlayerDao playerDao) throws Exception;

}
