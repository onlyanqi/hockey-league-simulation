package simulation.factory;

import db.data.ITeamDao;
import simulation.model.ITeam;
import simulation.model.Team;

public interface ITeamFactory {

    ITeam newTeam();

    ITeam newTeamByName(String name, ITeamDao loadTeamFactory) throws Exception;

}
