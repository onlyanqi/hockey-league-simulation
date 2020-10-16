package factory;

import dao.AddLeagueDao;
import dao.LoadLeagueDao;
import simulation.data.IAddLeagueFactory;
import simulation.data.ILoadLeagueFactory;
import simulation.model.League;

public class LeagueConcrete {

    public League newLeague(){
        return new League();
    }

    public ILoadLeagueFactory newLoadLeagueFactory(){
        return new LoadLeagueDao();
    }

    public League newLeagueNameUserId(String leagueName, int userId, ILoadLeagueFactory loadLeagueFactory) throws Exception {
        return new League(leagueName, userId, loadLeagueFactory);
    }

    public IAddLeagueFactory newAddLeagueFactory(){
        return new AddLeagueDao();
    }

}
