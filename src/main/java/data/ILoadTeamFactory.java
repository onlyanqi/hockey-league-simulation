package data;

import model.Team;

public interface ILoadTeamFactory {

    void loadTeamById(int id, Team team) throws Exception;
    Team loadTeamByName(String teamName) throws Exception;

}
