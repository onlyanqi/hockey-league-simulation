package simulation.dao;

import simulation.model.ITeamStanding;

public interface ITeamStandingDao {
    long addTeamStanding(int leagueId, ITeamStanding teamStanding) throws Exception;

    void loadTeamStandingById(int id,  ITeamStanding teamStanding) throws Exception;

    ITeamStanding loadTeamStandingByLeagueId(int leagueId) throws Exception;
}
