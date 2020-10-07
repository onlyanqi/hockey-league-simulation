package data;

import model.Team;

public interface ITeamFactory {

    int addTeam(Team team) throws Exception;
    void loadTeamByName(int id, Team team) throws Exception;

}
