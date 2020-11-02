package db.dao;

import db.data.IFreeAgentFactory;
import simulation.model.FreeAgent;

import java.sql.SQLException;

public class FreeAgentDao extends DBExceptionLog implements IFreeAgentFactory {
    @Override
    public int addFreeAgent(FreeAgent freeAgent) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddFreeAgent(?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, freeAgent.getLeagueId());
            callDB.setOutputParameterInt(2);
            callDB.execute();
            freeAgent.setId(callDB.returnOutputParameterInt(2));

        } catch (SQLException sqlException) {
            printLog("FreeAgentDao: addFreeAgent: SQLException: "+ sqlException);
            throw sqlException;
        } finally {
            if(getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return freeAgent.getId();
    }

    @Override
    public void loadFreeAgentById(int id, FreeAgent freeAgent) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadFreeAgentByLeagueId(?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterInt(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();

            freeAgent.setId(callDB.returnOutputParameterInt(2));
            freeAgent.setLeagueId(callDB.returnOutputParameterInt(3));
            freeAgent.setSeasonId(callDB.returnOutputParameterInt(4));

        } catch (SQLException sqlException) {
            printLog("FreeAgentDao: addFreeAgent: SQLException: "+ sqlException);
            throw sqlException;
        } finally {
            if(getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

    @Override
    public FreeAgent loadFreeAgentByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        FreeAgent freeAgent = null;
        try {
            String procedureName = "LoadFreeAgentByLeagueId(?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterInt(3);
            callDB.executeLoad();

            freeAgent = new FreeAgent();
            freeAgent.setId(callDB.returnOutputParameterInt(2));
            freeAgent.setLeagueId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException) {
            printLog("FreeAgentDao: addFreeAgent: SQLException: "+ sqlException);
            throw sqlException;
        } finally {
            if(getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return freeAgent;
    }
}
