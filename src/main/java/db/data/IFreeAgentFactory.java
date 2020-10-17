package db.data;

import simulation.model.FreeAgent;

public interface IFreeAgentFactory {

    int addFreeAgent(FreeAgent freeAgent) throws Exception;
    void loadFreeAgentById(int id, FreeAgent freeAgent) throws Exception;
    FreeAgent loadFreeAgentByLeagueId(int id) throws Exception;
}
