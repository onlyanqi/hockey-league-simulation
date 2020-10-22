package db.data;

import simulation.model.Coach;

public interface ICoachFactory {
    int addCoach(Coach coach) throws Exception;
    void loadCoachById(int id, Coach coach) throws Exception;
    Coach loadCoachByLeagueId(int id) throws Exception;
}

