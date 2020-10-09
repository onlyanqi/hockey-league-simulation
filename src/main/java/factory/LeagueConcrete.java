package factory;

import dao.AddFreeAgentDao;
import dao.AddLeagueDao;
import dao.LoadFreeAgentDao;
import dao.LoadLeagueDao;
import data.IAddFreeAgentFactory;
import data.IAddLeagueFactory;
import data.ILoadFreeAgentFactory;
import data.ILoadLeagueFactory;
import model.FreeAgent;
import model.League;

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
