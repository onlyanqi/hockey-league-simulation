package db.data;

import simulation.model.Game;
import simulation.model.ITeamStanding;
import simulation.model.TeamScore;
import simulation.model.TeamStanding;

import java.util.List;

public interface ITeamStandingDao {
    long addTeamStanding(int leagueId, ITeamStanding teamStanding) throws Exception;

    void loadTeamStandingById(int id,  ITeamStanding teamStanding) throws Exception;

    ITeamStanding loadTeamStandingByLeagueId(int leagueId) throws Exception;
}
