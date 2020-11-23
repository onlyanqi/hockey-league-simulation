package simulation.factory;

import db.dao.LeagueDao;
import db.data.ILeagueDao;
import db.data.IUserDao;
import simulation.model.ILeague;
import simulation.model.League;

public class LeagueConcrete implements ILeagueFactory{

    public ILeague newLeague() {
        return new League();
    }

    public ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception {
        return new League(leagueName, userId, leagueFactory);
    }

    public ILeagueDao newLeagueDao(){
        return new LeagueDao();
    }

    @Override
    public ILeague newLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception {
        return new League(id, leagueDao);
    }

}
