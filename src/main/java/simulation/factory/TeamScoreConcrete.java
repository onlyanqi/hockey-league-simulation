package simulation.factory;

import db.dao.TeamScoreDao;
import db.data.ITeamScoreFactory;
import simulation.model.TeamScore;

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
