package db.dao;

import db.data.IManagerFactory;
import simulation.model.Manager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ManagerDao extends DBExceptionLog implements IManagerFactory {

    @Override
    public int addManager(Manager manager) throws Exception {
        if(manager == null){
            return -1;
        }
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddManager(?,?,?,?)");
            callDB.setInputParameterInt(1, manager.getTeamId());
            callDB.setInputParameterString(2, manager.getName());
            callDB.setInputParameterInt(3, manager.getLeagueId());
            callDB.setOutputParameterInt(4);
            callDB.execute();
            manager.setId(callDB.returnOutputParameterInt(4));

        } catch (SQLException sqlException) {
            printLog("ManagerDao: addManager: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return manager.getId();
    }

    @Override
    public void loadManagerById(int managerId, Manager manager) throws Exception {
        if(manager == null){
            return;
        }
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadManagerById(?)");
            callDB.setInputParameterInt(1, managerId);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                manager = new Manager();
                manager.setId(rs.getInt(1));
                manager.setTeamId(rs.getInt(2));
                manager.setName(rs.getString(3));
                manager.setLeagueId(rs.getInt(4));
            }
        }catch (SQLException sqlException) {
            printLog("ManagerDao: loadManagerById: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public List<Manager> loadFreeManagersByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        List<Manager> managers = null;
        try {
            callDB = new CallDB("LoadManagerListByLeagueIdd(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                while (rs.next()) {
                    Manager manager = new Manager();
                    manager = new Manager();
                    manager.setId(rs.getInt(1));
                    manager.setTeamId(rs.getInt(2));
                    manager.setName(rs.getString(3));
                    manager.setLeagueId(rs.getInt(4));
                    managers.add(manager);
                }
            }
        }catch (SQLException sqlException) {
            printLog("ManagerDao: loadFreeManagersByLeagueId: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return managers;
    }

    @Override
    public Manager loadManagerByTeamId(int teamId) throws Exception {
        ICallDB callDB = null;
        Manager manager = null;
        try {
            callDB = new CallDB("LoadManagerByTeamId(?)");
            callDB.setInputParameterInt(1, teamId);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                manager = new Manager();
                manager.setId(rs.getInt(1));
                manager.setTeamId(rs.getInt(2));
                manager.setName(rs.getString(3));
                manager.setLeagueId(rs.getInt(4));
            }
        }catch (SQLException sqlException) {
            printLog("ManagerDao: loadManagerByTeamId: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return manager;
    }
}
