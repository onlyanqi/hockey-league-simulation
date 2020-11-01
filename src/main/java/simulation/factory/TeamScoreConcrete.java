package simulation.factory;

import db.dao.TeamDao;
import db.dao.TeamScoreDao;
import db.data.ITeamFactory;
import db.data.ITeamScoreFactory;
import simulation.model.Team;
import simulation.model.TeamScore;
import simulation.model.TeamStanding;

public class TeamScoreConcrete {

    public TeamScore newTeamScore() {
        return new TeamScore();
    }

    public ITeamScoreFactory newLoadTeamScoreFactory() {
        return new TeamScoreDao();
    }

    public ITeamScoreFactory newAddTeamScoreFactory() {
        return new TeamScoreDao();
    }
}
