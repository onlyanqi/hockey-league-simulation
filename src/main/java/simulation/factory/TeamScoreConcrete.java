package simulation.factory;

import db.data.ITeamScoreFactory;
import simulation.model.TeamScore;

public class TeamScoreConcrete {

    public TeamScore newTeamScore() {
        return new TeamScore();
    }

}
