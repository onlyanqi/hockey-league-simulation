package simulation.factory;

import simulation.dao.TeamDao;
import simulation.dao.ITeamDao;
import simulation.model.ITeam;
import simulation.model.Team;

public class TeamConcrete implements ITeamFactory{

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
        return new TeamDao();
    }

}
