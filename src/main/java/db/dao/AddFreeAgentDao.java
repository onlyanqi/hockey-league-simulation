package dao;

import common.Constants;
import simulation.data.IAddFreeAgentFactory;
import simulation.model.FreeAgent;

public class AddFreeAgentDao implements IAddFreeAgentFactory {
    @Override
    public int addFreeAgent(FreeAgent freeAgent) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.addFreeAgent);
            callDB.setInputParameterInt(1, freeAgent.getLeagueId());
            callDB.setInputParameterInt(2, freeAgent.getSeasonId());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            freeAgent.setId(callDB.returnOutputParameterInt(3));

        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
        return freeAgent.getId();


    }
}
