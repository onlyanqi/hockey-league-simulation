package simulation.factory;

import simulation.dao.ILeagueDao;
import simulation.mock.LeagueMock;
import simulation.model.ILeague;
import simulation.model.League;

public class LeagueConcreteMock implements ILeagueFactory {

    public ILeague newLeague() {
        return new League();
    }

    public ILeague createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueDao leagueFactory) throws Exception {
        return new League(leagueName, userId, leagueFactory);
    }

    public ILeagueDao newLeagueDao(){
        return new LeagueMock();
    }

    @Override
    public ILeague newLeagueWithIdDao(int id, ILeagueDao leagueDao) throws Exception {
        return new League(id, leagueDao);
    }

}
