package db.dao;

import db.data.IInjuryFactory;
import simulation.model.Injury;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InjuryDao extends DBExceptionLog implements IInjuryFactory {
    @Override
    public int addInjury(Injury injury) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddInjury(?,?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterDouble(1, injury.getRandomInjuryChance());
            callDB.setInputParameterInt(2, injury.getInjuryDaysLow());
            callDB.setInputParameterInt(3, injury.getInjuryDaysHigh());
            callDB.setInputParameterInt(4, injury.getLeagueId());
            callDB.setOutputParameterInt(5);
            callDB.execute();
            injury.setId(callDB.returnOutputParameterInt(5));

        } catch (SQLException sqlException) {
            printLog("InjuryDao: addInjury: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return injury.getId();
    }

    @Override
    public Injury loadInjuryByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        Injury injury = new Injury();
        try {
            String procedureName = "LoadInjuryByLeagueId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();

            if (getValidation().isNotNull(rs)) {
                injury = new Injury();
                injury.setId(rs.getInt(1));
                injury.setRandomInjuryChance(rs.getDouble(2));
                injury.setInjuryDaysLow(rs.getInt(3));
                injury.setInjuryDaysHigh(rs.getInt(4));
                injury.setLeagueId(rs.getInt(5));
            }

        } catch (SQLException sqlException) {
            printLog("InjuryDao: loadInjuryByLeagueId: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return injury;
    }

    @Override
    public void loadInjuryById(int id, Injury injury) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadInjuryById(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();

            if (getValidation().isNotNull(rs)) {
                injury = new Injury();
                injury.setId(rs.getInt(1));
                injury.setRandomInjuryChance(rs.getDouble(2));
                injury.setInjuryDaysLow(rs.getInt(3));
                injury.setInjuryDaysHigh(rs.getInt(4));
                injury.setLeagueId(rs.getInt(5));
            }

        } catch (SQLException sqlException) {
            printLog("InjuryDao: loadInjuryById: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }
}
