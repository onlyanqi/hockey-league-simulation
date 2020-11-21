package simulation.factory;

import db.data.ITeamDao;
import simulation.model.ITeam;
import simulation.model.Team;

public class TeamConcrete implements ITeamFactory{

    public ITeam newTeam() {
        return new Team();
    }

    public ITeam newTeamByName(String name, ITeamDao loadTeamFactory) throws Exception {
        return new Team(name, loadTeamFactory);
    }

}
