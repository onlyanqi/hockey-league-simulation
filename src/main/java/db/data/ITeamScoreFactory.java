package db.data;


import simulation.model.TeamScore;
import simulation.model.TeamStanding;

import java.util.List;

public interface ITeamScoreFactory {
    long addTeamScore(int leagueId, int regularSeason, TeamScore teamScore) throws Exception;

    void loadTeamScoreById(int id, TeamScore teamScore) throws Exception;

    List<TeamScore> loadRegularTeamScoreListByLeagueId(int leagueId) throws Exception;

    List<TeamScore> loadPlayoffTeamScoreListByLeagueId(int leagueId) throws Exception;

}
