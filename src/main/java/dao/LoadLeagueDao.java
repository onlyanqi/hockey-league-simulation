package dao;

import common.Constants;
import data.ILoadLeagueFactory;
import model.League;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadLeagueDao implements ILoadLeagueFactory {

    @Override
    public void loadLeagueById(int id, League league) throws Exception{
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadLeagueByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    league = new League();
                    league.setId(rs.getInt(2));
                    league.setName(rs.getString(3));
                    league.setCreatedBy(rs.getInt(4));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public League loadLeagueByName(String leagueName) throws Exception {
        ICallDB callDB = null;
        League league = null;
        try {
            callDB = new CallDB(Constants.loadLeagueByName);
            callDB.setInputParameterString(1, leagueName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    league = new League();
                    league.setId(rs.getInt(2));
                    league.setName(rs.getString(3));
                    league.setCreatedBy(rs.getInt(4));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
        return league;
    }

    @Override
    public List<League> loadLeagueListByUserId(int userId) throws Exception {
        List<League> leagueList = null;
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.loadLeagueListByUserId);
            callDB.setInputParameterInt(1, userId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                leagueList = new ArrayList<>();
                while (rs.next()) {
                    League league = new League();
                    league.setId(rs.getInt(1));
                    league.setName(rs.getString(2));
                    league.setCreatedBy(rs.getInt(3));
                    leagueList.add(league);
                }
            }
        } catch (Exception e){
            throw e;
        }

        return leagueList;
    }

}
