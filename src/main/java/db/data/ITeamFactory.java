package db.data;

import simulation.model.Team;

import java.util.List;

public interface ITeamFactory {

    int addTeam(Team team) throws Exception;
    void loadTeamById(int id, Team team) throws Exception;
    void loadTeamByName(String teamName, Team team) throws Exception;
    List<Team> loadTeamListByDivisionId(int divisionId) throws Exception;
}
