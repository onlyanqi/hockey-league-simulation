package simulation.factory;

import simulation.dao.ILeagueDao;
import simulation.model.ILeague;

public interface ILeagueFactory {

    ILeague newLeague();

    ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception;

    ILeagueDao newLeagueDao();

    ILeague newLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception;
}
