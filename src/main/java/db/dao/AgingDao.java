package db.dao;

import db.data.IAgingFactory;
import simulation.model.Aging;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AgingDao implements IAgingFactory {

    @Override
    public int addAging(Aging aging) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddAging(?,?,?,?)");
            callDB.setInputParameterInt(1, aging.getAverageRetirementAge());
            callDB.setInputParameterInt(2, aging.getMaximumAge());
            callDB.setInputParameterInt(3, aging.getLeagueId());
            callDB.setOutputParameterInt(4);
            callDB.execute();
            aging.setId(callDB.returnOutputParameterInt(4));

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
        return aging.getId();
    }

    @Override
    public Aging loadAgingByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        Aging aging = null;
        try {
            callDB = new CallDB("LoadAgingByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();

            if (rs != null) {
                aging = new Aging();
                aging.setId(rs.getInt(1));
                aging.setAverageRetirementAge(rs.getInt(2));
                aging.setMaximumAge(rs.getInt(3));
                aging.setLeagueId(rs.getInt(4));
            }

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
        return aging;
    }

    @Override
    public void loadAgingById(int id, Aging aging) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadAgingById(?)");
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();

            if (rs != null) {
                aging.setId(rs.getInt(1));
                aging.setAverageRetirementAge(rs.getInt(2));
                aging.setMaximumAge(rs.getInt(3));
                aging.setLeagueId(rs.getInt(4));
            }

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
    }
}
