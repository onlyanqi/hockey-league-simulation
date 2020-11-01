package simulation.factory;

import db.dao.TeamStandingDao;
import db.data.ITeamStandingFactory;

public class TeamStandingConcrete {

    public ITeamStandingFactory newAddTeamStandingFactory() {
        return new TeamStandingDao();
    }
}
