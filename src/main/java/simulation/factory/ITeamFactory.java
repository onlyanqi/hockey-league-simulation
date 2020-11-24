package simulation.factory;

import simulation.dao.ITeamDao;
import simulation.model.ITeam;

public interface ITeamFactory {

    ITeam newTeam();

    ITeam newTeamByName(String name, ITeamDao loadTeamFactory) throws Exception;

    ITeam newTeamWithIdDao(int id, ITeamDao teamDao) throws Exception;

    ITeamDao newTeamDao();
}
