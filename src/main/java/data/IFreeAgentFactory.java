package data;

import model.FreeAgent;

public interface IFreeAgentFactory {

    int addFreeAgent(FreeAgent freeAgent) throws Exception;
    void loadFreeAgentByLeagueId(int id, FreeAgent freeAgent) throws Exception;

}
