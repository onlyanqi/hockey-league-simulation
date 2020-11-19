package db.data;

import simulation.model.Aging;

public interface IAgingDao {
    int addAging(Aging aging) throws Exception;

    Aging loadAgingByLeagueId(int leagueId) throws Exception;

    void loadAgingById(int id, Aging aging) throws Exception;
}
