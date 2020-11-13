package db.data;

import simulation.model.Game;
import simulation.model.TeamScore;
import simulation.model.TeamStanding;

import java.util.List;

public interface ITeamStandingFactory {
    long addTeamStanding(int leagueId, TeamStanding teamStanding) throws Exception;

    void loadTeamStandingById(int id,  TeamStanding teamStanding) throws Exception;

    TeamStanding loadTeamStandingByLeagueId(int leagueId) throws Exception;
}
