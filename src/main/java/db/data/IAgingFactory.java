package db.data;

import simulation.model.Aging;

public interface IAgingFactory {
    int addAging(Aging aging) throws Exception;
    Aging loadAgingByLeagueId(int id) throws Exception;
    void loadAgingById(int id, Aging aging);
}
