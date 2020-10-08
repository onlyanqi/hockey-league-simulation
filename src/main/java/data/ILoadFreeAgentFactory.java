package data;

import model.FreeAgent;

public interface ILoadFreeAgentFactory {

    void loadFreeAgentByLeagueId(int leagueId, FreeAgent freeAgent) throws Exception;

}
