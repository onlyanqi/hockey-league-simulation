package db.dao;

import db.data.ITeamScoreDao;
import simulation.model.ITeamScore;

import java.util.List;

public class TeamScoreDao extends DBExceptionLog implements ITeamScoreDao {


    @Override
    public long addTeamScore(int leagueId, int regularSeason, ITeamScore teamScore) throws Exception {
        return 0;
    }

    @Override
    public void loadTeamScoreById(int id, ITeamScore teamScore) throws Exception {

    }

    @Override
    public List<ITeamScore> loadRegularTeamScoreListByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public List<ITeamScore> loadPlayoffTeamScoreListByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public void updateTeamScoreById(ITeamScore teamScore) throws Exception {

    }
}
