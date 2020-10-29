package db.data;

import simulation.model.Coach;

import java.util.List;

public interface ICoachFactory {
    int addCoach(Coach coach) throws Exception;

    void loadCoachById(int id, Coach coach) throws Exception;

    Coach loadCoachByTeamId(int teamId) throws Exception;

    List<Coach> loadFreeCoachListByLeagueId(int leagueId) throws Exception;
}

