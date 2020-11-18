package simulation.factory;

import db.data.ITeamFactory;
import simulation.model.Team;

public class TeamConcrete {

    public Team newTeam() {
        return new Team();
    }

    public Team newTeamByName(String name, ITeamFactory loadTeamFactory) throws Exception {
        return new Team(name, loadTeamFactory);
    }

}
