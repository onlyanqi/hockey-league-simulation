package db.dao;

import db.data.IManagerFactory;
import simulation.model.Manager;

import java.sql.SQLException;

public class ManagerDao implements IManagerFactory {
    @Override
    public int addManager(Manager manager) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB("AddManager(?,?,?,?)");
            callDB.setInputParameterInt(1, manager.getTeamId());
            callDB.setInputParameterString(2, manager.getName());
            callDB.setInputParameterInt(3, manager.getLeagueId());


            callDB.setOutputParameterInt(4);
            callDB.execute();
            manager.setId(callDB.returnOutputParameterInt(4));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return manager.getId();
    }

    @Override
    public void loadManagerById(int id, Manager manager) throws Exception {

    }

    @Override
    public Manager loadManagerByLeagueId(int id) throws Exception {
        return null;
    }
}
