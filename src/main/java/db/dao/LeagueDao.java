package db.dao;

import db.data.ILeagueFactory;
import simulation.model.League;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeagueDao implements ILeagueFactory {

    @Override
    public int addLeague(League league) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddLeague(?,?,?)");
            callDB.setInputParameterString(1, league.getName());
            callDB.setInputParameterInt(2, league.getCreatedBy());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            league.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException) {
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


        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public List<League> loadLeagueListByUserId(int userId) throws Exception {
        List<League> leagueList = null;
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadLeagueListByUserId(?)");
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
        } catch (Exception e) {
            throw e;
        }

        return leagueList;
    }


}
