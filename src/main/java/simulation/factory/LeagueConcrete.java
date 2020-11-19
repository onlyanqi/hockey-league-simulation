package simulation.factory;

import db.data.ILeagueFactory;
import simulation.model.League;

public class LeagueConcrete {

    public League newLeague() {
        return new League();
    }

    public League createLeagueFromNameAndUserId(String leagueName, int userId, ILeagueFactory leagueFactory) throws Exception {
        return new League(leagueName, userId, leagueFactory);
    }


}
