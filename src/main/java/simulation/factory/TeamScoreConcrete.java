package simulation.factory;

import simulation.model.ITeamScore;
import simulation.model.TeamScore;

public class TeamScoreConcrete {

    public ITeamScore newTeamScore() {
        return new TeamScore();
    }

}
