package db.dao;

import db.data.IEventFactory;
import simulation.model.Game;
import simulation.model.NHLEvents;
import util.DateUtil;

import java.awt.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventDao implements IEventFactory {
    @Override
    public long addEvent(int leagueId, NHLEvents events) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddEvent(?,?,?,?,?,?,?,?)");
            callDB.setInputParameterInt(1, leagueId);
            callDB.setInputParameterDate(2, DateUtil.convertLocalDateToSQLDate(events.getRegularSeasonStartDate()));
            callDB.setInputParameterDate(3, DateUtil.convertLocalDateToSQLDate(events.getTradeDeadlineDate()));
            callDB.setInputParameterDate(4, DateUtil.convertLocalDateToSQLDate(events.getEndOfRegularSeason()));
            callDB.setInputParameterDate(5, DateUtil.convertLocalDateToSQLDate(events.getPlayOffStartDate()));
            callDB.setInputParameterDate(6, DateUtil.convertLocalDateToSQLDate(events.getLastDayStanleyCupFinals()));
            callDB.setInputParameterDate(7, DateUtil.convertLocalDateToSQLDate(events.getNextSeasonDate()));
            callDB.setOutputParameterInt(8);
            callDB.execute();
            events.setId(callDB.returnOutputParameterInt(8));

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
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
            if(rs!=null){
                nhlEvents.setId(rs.getInt(1));
                nhlEvents.setRegularSeasonStartDate(rs.getDate(2).toLocalDate());
                nhlEvents.setTradeDeadlineDate(rs.getDate(3).toLocalDate());
                nhlEvents.setEndOfRegularSeason(rs.getDate(4).toLocalDate());
                nhlEvents.setPlayOffStartDate(rs.getDate(5).toLocalDate());
                nhlEvents.setLastDayStanleyCupFinals(rs.getDate(6).toLocalDate());
                nhlEvents.setNextSeasonDate(rs.getDate(7).toLocalDate());
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public void loadEventByLeagueId(int leagueId, NHLEvents nhlEvents) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadEventByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                nhlEvents.setId(rs.getInt(1));
                nhlEvents.setRegularSeasonStartDate(rs.getDate(2).toLocalDate());
                nhlEvents.setTradeDeadlineDate(rs.getDate(3).toLocalDate());
                nhlEvents.setEndOfRegularSeason(rs.getDate(4).toLocalDate());
                nhlEvents.setPlayOffStartDate(rs.getDate(5).toLocalDate());
                nhlEvents.setLastDayStanleyCupFinals(rs.getDate(6).toLocalDate());
                nhlEvents.setNextSeasonDate(rs.getDate(7).toLocalDate());
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }
}
