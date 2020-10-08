package dao;

import common.Constants;
import data.ILoadFreeAgentFactory;
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
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    freeAgent = new FreeAgent();
                    freeAgent.setId(rs.getInt(2));
                    freeAgent.setLeagueId(rs.getInt(3));
                    freeAgent.setSeasonId(rs.getInt(4));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }
}
