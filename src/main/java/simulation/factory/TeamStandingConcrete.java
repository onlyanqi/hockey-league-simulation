package simulation.factory;

import db.dao.TeamDao;
import db.dao.TeamStandingDao;
import db.data.ITeamFactory;
import db.data.ITeamStandingFactory;
import simulation.model.Team;
import simulation.model.TeamStanding;

public class TeamStandingConcrete {

    public TeamStanding newTeamStanding() {
        return new TeamStanding();
    }

    public ITeamStandingFactory newLoadTeamStandingFactory() {
        return new TeamStandingDao();
    }

    public ITeamStandingFactory newAddTeamStandingFactory() {
        return new TeamStandingDao();
    }
}
