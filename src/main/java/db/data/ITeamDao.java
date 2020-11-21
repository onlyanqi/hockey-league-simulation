package db.data;

import simulation.model.ITeam;
import simulation.model.Team;

import java.util.List;

public interface ITeamDao {

    int addTeam(ITeam team) throws Exception;

    void loadTeamById(int id, ITeam team) throws Exception;

    void loadTeamByName(String teamName, ITeam team) throws Exception;

    List<ITeam> loadTeamListByDivisionId(int divisionId) throws Exception;

    void updateTeamById(ITeam team) throws Exception;
}
