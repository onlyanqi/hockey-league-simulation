package db.dao;

import db.data.ICoachDao;
import simulation.model.ICoach;

import java.util.List;

public class CoachDao extends DBExceptionLog implements ICoachDao {

    @Override
    public int addCoach(ICoach coach) throws Exception {
        return 0;
    }

    @Override
    public void loadCoachById(int id, ICoach coach) throws Exception {

    }

    @Override
    public ICoach loadCoachByTeamId(int teamId) throws Exception {
        return null;
    }

    @Override
    public List<ICoach> loadFreeCoachListByLeagueId(int leagueId) throws Exception {
        return null;
    }
}
