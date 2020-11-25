package simulation.dao;

import simulation.model.ICoach;

import java.util.List;

public interface ICoachDao {

    int addCoach(ICoach coach) throws Exception;

    void loadCoachById(int id, ICoach coach) throws Exception;

    ICoach loadCoachByTeamId(int teamId) throws Exception;

    List<ICoach> loadFreeCoachListByLeagueId(int leagueId) throws Exception;
}

