package db.dao;

import db.data.IFreeAgentFactory;
import simulation.model.FreeAgent;

public class FreeAgentDao implements IFreeAgentFactory {
    @Override
    public int addFreeAgent(FreeAgent freeAgent) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddFreeAgent(?,?)");
            callDB.setInputParameterInt(1, freeAgent.getLeagueId());
            callDB.setOutputParameterInt(2);
            callDB.execute();
            freeAgent.setId(callDB.returnOutputParameterInt(2));

        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
        return freeAgent.getId();
    }

    @Override
    public void loadFreeAgentById(int id, FreeAgent freeAgent) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadFreeAgentByLeagueId(?,?,?,?)");
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterInt(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();


            freeAgent.setId(callDB.returnOutputParameterInt(2));
            freeAgent.setLeagueId(callDB.returnOutputParameterInt(3));
            freeAgent.setSeasonId(callDB.returnOutputParameterInt(4));

        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public FreeAgent loadFreeAgentByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        FreeAgent freeAgent = null;
        try {
            callDB = new CallDB("LoadFreeAgentByLeagueId(?,?,?)");
            callDB.setInputParameterInt(1, leagueId);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterInt(3);
            callDB.executeLoad();

            freeAgent = new FreeAgent();
            freeAgent.setId(callDB.returnOutputParameterInt(2));
            freeAgent.setLeagueId(callDB.returnOutputParameterInt(3));

        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
        return freeAgent;
    }
}
