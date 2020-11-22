package simulation.factory;

import simulation.model.ITeamScore;
import simulation.model.TeamScore;

public interface ITeamScoreFactory {

    ITeamScore newTeamScore();

}
