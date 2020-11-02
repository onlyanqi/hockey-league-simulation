package db.dao;

import db.data.ILeagueFactory;
import simulation.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeagueDao extends DBExceptionLog implements ILeagueFactory {

    @Override
    public int addLeague(League league) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddLeague(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            callDB.setInputParameterString(1, league.getName());
            callDB.setInputParameterInt(2, league.getCreatedBy());
            callDB.setInputParameterInt(3, league.getGamePlayConfig().getAging().getAverageRetirementAge());
            callDB.setInputParameterInt(4, league.getGamePlayConfig().getAging().getMaximumAge());
            callDB.setInputParameterDouble(5, league.getGamePlayConfig().getGameResolver().getRandomWinChance());
            callDB.setInputParameterDouble(6, league.getGamePlayConfig().getInjury().getRandomInjuryChance());
            callDB.setInputParameterInt(7, league.getGamePlayConfig().getInjury().getInjuryDaysLow());
            callDB.setInputParameterInt(8, league.getGamePlayConfig().getInjury().getInjuryDaysHigh());
            callDB.setInputParameterInt(9, league.getGamePlayConfig().getTraining().getDaysUntilStatIncreaseCheck());
            callDB.setInputParameterInt(10, league.getGamePlayConfig().getTrading().getLossPoint());
            callDB.setInputParameterDouble(11, league.getGamePlayConfig().getTrading().getRandomTradeOfferChance());
            callDB.setInputParameterInt(12, league.getGamePlayConfig().getTrading().getMaxPlayersPerTrade());
            callDB.setInputParameterInt(13, league.getGamePlayConfig().getTrading().getMaxPlayersPerTrade());
            callDB.setInputParameterDate(14, DateTime.convertLocalDateToSQLDate(league.getCurrentDate()));

            callDB.setOutputParameterInt(15);
            callDB.execute();
            league.setId(callDB.returnOutputParameterInt(15));

        } catch (SQLException sqlException) {
            printLog("LeagueDao: addLeague: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return league.getId();
    }

    @Override
    public void loadLeagueById(int id, League league) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadLeagueByName(?,?,?,?)");
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);

            callDB.executeLoad();

            league.setId(callDB.returnOutputParameterInt(2));
            league.setName(callDB.returnOutputParameterString(3));
            league.setCreatedBy(callDB.returnOutputParameterInt(4));


        } catch (SQLException e) {
            printLog("LeagueDao: loadLeagueById: SQLException: " + e);
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public void loadLeagueByName(String leagueName, int userId, League league) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("loadLeagueByNameUserId(?,?)");
            callDB.setInputParameterString(1, leagueName);
            callDB.setInputParameterInt(2, userId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    league.setId(rs.getInt(1));
                    league.setName(rs.getString(2));
                    league.setCreatedBy(rs.getInt(3));
                }
            }
        } catch (SQLException e) {
            printLog("LeagueDao: loadLeagueByName: SQLException: " + e);
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public List<League> loadLeagueListByUserId(int userId) throws Exception {
        List<League> leagueList = null;
        ICallDB callDB;
        try {
            callDB = new CallDB("LoadLeagueListByUserId(?)");
            callDB.setInputParameterInt(1, userId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                leagueList = new ArrayList<>();
                while (rs.next()) {
                    League league = new League();
                    Aging aging = new Aging();
                    GameResolver gameResolver = new GameResolver();
                    Injury injuries = new Injury();
                    Training training = new Training();
                    Trading trading = new Trading();
                    GamePlayConfig gamePlayConfig = new GamePlayConfig();

                    league.setId(rs.getInt(1));
                    league.setName(rs.getString(2));
                    league.setCreatedBy(rs.getInt(3));
                    aging.setAverageRetirementAge(rs.getInt(4));
                    aging.setMaximumAge(rs.getInt(5));
                    gameResolver.setRandomWinChance(rs.getInt(6));
                    injuries.setRandomInjuryChance(rs.getDouble(7));
                    injuries.setInjuryDaysLow(rs.getInt(8));
                    injuries.setInjuryDaysHigh(rs.getInt(9));
                    training.setDaysUntilStatIncreaseCheck(rs.getInt(10));
                    trading.setLossPoint(rs.getInt(11));
                    trading.setRandomTradeOfferChance(rs.getInt(12));
                    trading.setMaxPlayersPerTrade(rs.getInt(13));
                    trading.setRandomAcceptanceChance(rs.getInt(14));
                    gamePlayConfig.setAging(aging);
                    gamePlayConfig.setGameResolver(gameResolver);
                    gamePlayConfig.setInjury(injuries);
                    gamePlayConfig.setTrading(trading);
                    gamePlayConfig.setTraining(training);
                    league.setGamePlayConfig(gamePlayConfig);
                    league.setCurrentDate(rs.getDate(15).toLocalDate());
                    leagueList.add(league);
                }
            }
        } catch (SQLException e) {
            printLog("LeagueDao: loadLeagueListByUserId: SQLException: " + e);
            throw e;
        }

        return leagueList;
    }


}
