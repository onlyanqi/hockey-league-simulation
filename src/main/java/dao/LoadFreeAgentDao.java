package dao;

import common.Constants;
import data.ILoadFreeAgentFactory;
import model.Division;
import model.FreeAgent;

import java.sql.ResultSet;

public class LoadFreeAgentDao implements ILoadFreeAgentFactory {

    @Override
    public void loadFreeAgentByLeagueId(int leagueId, FreeAgent freeAgent) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadFreeAgentByLeagueId);
            callDB.setInputParameterInt(1, leagueId);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterInt(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();

            freeAgent = new FreeAgent();
            freeAgent.setId(callDB.returnOutputParameterInt(2));
            freeAgent.setLeagueId(callDB.returnOutputParameterInt(3));
            freeAgent.setSeasonId(callDB.returnOutputParameterInt(4));

        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }
}
