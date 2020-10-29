package db.data;


import simulation.model.TeamScore;
import simulation.model.TeamStanding;

public interface ITeamStandingFactory {
    long addTeamStanding(int leagueId, TeamScore teamScore) throws Exception;

    void loadTeamStandingById(int id, TeamScore teamScore) throws Exception;

    void loadTeamStandingByLeagueId(int leagueId, TeamScore teamScore) throws Exception;
}
