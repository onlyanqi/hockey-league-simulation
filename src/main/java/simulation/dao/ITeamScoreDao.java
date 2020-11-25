package simulation.dao;


import simulation.model.ITeamScore;

import java.util.List;

public interface ITeamScoreDao {

    long addTeamScore(int leagueId, int regularSeason, ITeamScore teamScore) throws Exception;

    void loadTeamScoreById(int id, ITeamScore teamScore) throws Exception;

    List<ITeamScore> loadRegularTeamScoreListByLeagueId(int leagueId) throws Exception;

    List<ITeamScore> loadPlayoffTeamScoreListByLeagueId(int leagueId) throws Exception;

    void updateTeamScoreById(ITeamScore teamScore) throws Exception;
}
