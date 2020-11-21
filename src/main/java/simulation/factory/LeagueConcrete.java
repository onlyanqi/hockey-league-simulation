package simulation.factory;

import db.dao.LeagueDao;
import db.data.ILeagueDao;
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

}
