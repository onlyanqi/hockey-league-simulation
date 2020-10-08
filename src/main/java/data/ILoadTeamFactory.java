package data;

import model.Team;

import java.util.List;

public interface ILoadTeamFactory {

    void loadTeamById(int id, Team team) throws Exception;
    Team loadTeamByName(String teamName) throws Exception;

    List<Team> loadTeamListByDivisionId(int divisionId) throws Exception;
}
