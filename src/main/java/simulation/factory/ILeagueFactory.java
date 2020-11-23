package simulation.factory;

import db.data.ILeagueDao;
import db.data.IUserDao;
import simulation.model.ILeague;
import simulation.model.League;

public interface ILeagueFactory {

    ILeague newLeague();

    ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception;

    ILeagueDao newLeagueDao();

    ILeague newLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception;
}
