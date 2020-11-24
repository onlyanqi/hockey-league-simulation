package simulation.dao;

import simulation.model.IFreeAgent;

public class FreeAgentDao extends DBExceptionLog implements IFreeAgentDao {

    @Override
    public int addFreeAgent(IFreeAgent freeAgent) throws Exception {
        return 0;
    }

    @Override
    public void loadFreeAgentById(int id, IFreeAgent freeAgent) throws Exception {

    }

    @Override
    public IFreeAgent loadFreeAgentByLeagueId(int id) throws Exception {
        return null;
    }

}
