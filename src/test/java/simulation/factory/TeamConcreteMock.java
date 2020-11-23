package simulation.factory;

import db.dao.TeamDao;
import db.data.ITeamDao;
import simulation.factory.ITeamFactory;
import simulation.mock.TeamMock;
import simulation.model.ITeam;
import simulation.model.Team;

public class TeamConcreteMock implements ITeamFactory {

    public ITeam newTeam() {
        return new Team();
    }

    public ITeam newTeamByName(String name, ITeamDao loadTeamFactory) throws Exception {
        return new Team(name, loadTeamFactory);
    }

    public ITeam newTeamWithIdDao(int id, ITeamDao teamDao) throws Exception {
        return new Team(id, teamDao);
    }

    public ITeamDao newTeamDao(){
        return new TeamMock();
    }

}
