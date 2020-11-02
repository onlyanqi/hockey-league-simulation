package db.dao;

import db.data.IAgingFactory;
import simulation.model.Aging;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgingDao extends DBExceptionLog implements IAgingFactory {

    @Override
    public int addAging(Aging aging) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddAging(?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, aging.getAverageRetirementAge());
            callDB.setInputParameterInt(2, aging.getMaximumAge());
            callDB.setInputParameterInt(3, aging.getLeagueId());
            callDB.setOutputParameterInt(4);
            callDB.execute();
            aging.setId(callDB.returnOutputParameterInt(4));

        } catch (SQLException sqlException) {
            printLog("AgingDao: addAging: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return aging.getId();
    }

    @Override
    public Aging loadAgingByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        Aging aging = null;
        try {
            String procedureName = "LoadAgingByLeagueId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();

            if (getValidation().isNotNull(rs)) {
                aging = new Aging();
                aging.setId(rs.getInt(1));
                aging.setAverageRetirementAge(rs.getInt(2));
                aging.setMaximumAge(rs.getInt(3));
                aging.setLeagueId(rs.getInt(4));
            }

        } catch (SQLException sqlException) {
            printLog("AgingDao: loadAgingByLeagueId: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return aging;
    }

    @Override
    public void loadAgingById(int id, Aging aging) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadAgingById(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();

            if (getValidation().isNotNull(rs)) {
                aging.setId(rs.getInt(1));
                aging.setAverageRetirementAge(rs.getInt(2));
                aging.setMaximumAge(rs.getInt(3));
                aging.setLeagueId(rs.getInt(4));
            }

        } catch (SQLException sqlException) {
            printLog("AgingDao: loadAgingById: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }
}
