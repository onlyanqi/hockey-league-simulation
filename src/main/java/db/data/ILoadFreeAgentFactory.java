package simulation.data;

import simulation.model.FreeAgent;

public interface ILoadFreeAgentFactory {

    void loadFreeAgentById(int id, FreeAgent freeAgent) throws Exception;
    FreeAgent loadFreeAgentByLeagueId(int id) throws Exception;

}
