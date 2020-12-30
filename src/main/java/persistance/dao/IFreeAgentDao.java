package persistance.dao;

import simulation.model.IFreeAgent;

public interface IFreeAgentDao {

    int addFreeAgent(IFreeAgent freeAgent) throws Exception;

    void loadFreeAgentById(int id, IFreeAgent freeAgent) throws Exception;

    IFreeAgent loadFreeAgentByLeagueId(int id) throws Exception;
}
