package dao;

import common.Constants;
import data.IFreeAgentFactory;
import model.FreeAgent;

import java.sql.ResultSet;

public class FreeAgentDao implements IFreeAgentFactory {
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

    @Override
    public void loadFreeAgentByLeagueId(int id, FreeAgent freeAgent) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadFreeAgentByLeagueId);
            callDB.setInputParameterInt(1, freeAgent.getLeagueId());
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterInt(3);
            callDB.setOutputParameterInt(4);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
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
