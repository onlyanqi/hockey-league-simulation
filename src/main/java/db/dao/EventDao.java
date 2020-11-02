package db.dao;

import db.data.IEventFactory;
import simulation.model.DateTime;
import simulation.model.NHLEvents;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventDao extends DBExceptionLog implements IEventFactory {
    @Override
    public long addEvent(int leagueId, NHLEvents events) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddEvent(?,?,?,?,?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            callDB.setInputParameterDate(2, DateTime.convertLocalDateToSQLDate(events.getRegularSeasonStartDate()));
            callDB.setInputParameterDate(3, DateTime.convertLocalDateToSQLDate(events.getTradeDeadlineDate()));
            callDB.setInputParameterDate(4, DateTime.convertLocalDateToSQLDate(events.getEndOfRegularSeason()));
            callDB.setInputParameterDate(5, DateTime.convertLocalDateToSQLDate(events.getPlayOffStartDate()));
            callDB.setInputParameterDate(6, DateTime.convertLocalDateToSQLDate(events.getLastDayStanleyCupFinals()));
            callDB.setInputParameterDate(7, DateTime.convertLocalDateToSQLDate(events.getNextSeasonDate()));
            callDB.setOutputParameterInt(8);
            callDB.execute();
            events.setId(callDB.returnOutputParameterInt(8));

        } catch (SQLException sqlException) {
            printLog("EventDao: addEvent: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return events.getId();
    }

    @Override
    public void loadEventById(int id, NHLEvents nhlEvents) throws Exception {

        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadEventById(?)");
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();
            if (getValidation().isNotNull(rs)) {
                nhlEvents.setId(rs.getInt(1));
                nhlEvents.setRegularSeasonStartDate(rs.getDate(2).toLocalDate());
                nhlEvents.setTradeDeadlineDate(rs.getDate(3).toLocalDate());
                nhlEvents.setEndOfRegularSeason(rs.getDate(4).toLocalDate());
                nhlEvents.setPlayOffStartDate(rs.getDate(5).toLocalDate());
                nhlEvents.setLastDayStanleyCupFinals(rs.getDate(6).toLocalDate());
                nhlEvents.setNextSeasonDate(rs.getDate(7).toLocalDate());
            }
        } catch (SQLException sqlException) {
            printLog("EventDao: addEvent: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

    @Override
    public void loadEventByLeagueId(int leagueId, NHLEvents nhlEvents) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadEventByLeagueId(?)";
            callDB = new CallDB(procedureName);

            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (getValidation().isNotNull(rs)) {
                while (rs.next()) {
                    nhlEvents.setId(rs.getInt(1));
                    nhlEvents.setRegularSeasonStartDate(rs.getDate(2).toLocalDate());
                    nhlEvents.setTradeDeadlineDate(rs.getDate(3).toLocalDate());
                    nhlEvents.setEndOfRegularSeason(rs.getDate(4).toLocalDate());
                    nhlEvents.setPlayOffStartDate(rs.getDate(5).toLocalDate());
                    nhlEvents.setLastDayStanleyCupFinals(rs.getDate(6).toLocalDate());
                    nhlEvents.setNextSeasonDate(rs.getDate(7).toLocalDate());
                }
            }
        } catch (SQLException sqlException) {
            printLog("EventDao: addEvent: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }
}
