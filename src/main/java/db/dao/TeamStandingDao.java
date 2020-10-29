package db.dao;

import db.data.ITeamStandingFactory;
import simulation.model.Game;
import simulation.model.TeamScore;
import simulation.model.TeamStanding;

import java.sql.ResultSet;
import java.sql.SQLException;
public class TeamStandingDao implements ITeamStandingFactory {
    @Override
    public long addTeamStanding(int leagueId, TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddTeamStanding(?,?,?,?,?,?,?)");
            callDB.setInputParameterInt(1, leagueId);
            callDB.setInputParameterDouble(2, teamScore.getPoints());
            callDB.setInputParameterString(3, teamScore.getTeamName());
            callDB.setInputParameterInt(4, teamScore.getNumberOfWins());
            callDB.setInputParameterInt(5,teamScore.getNumberOfLoss());
            callDB.setInputParameterInt(6,teamScore.getNumberOfTies());

            callDB.setOutputParameterInt(7);
            callDB.execute();
            teamScore.setId(callDB.returnOutputParameterInt(7));

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return teamScore.getId();
    }

    @Override
    public void loadTeamStandingById(int id, TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadTeamStandingById(?)");
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                teamScore.setId(rs.getInt(1));
                teamScore.setPoints(rs.getInt(2));
                teamScore.setTeamName(rs.getString(3));
                teamScore.setNumberOfWins(rs.getInt(4));
                teamScore.setNumberOfLoss(rs.getInt(5));
                teamScore.setNumberOfTies(rs.getInt(6));
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public void loadTeamStandingByLeagueId(int leagueId, TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadTeamStandingByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if(rs!=null){
                teamScore.setId(rs.getInt(1));
                teamScore.setPoints(rs.getInt(2));
                teamScore.setTeamName(rs.getString(3));
                teamScore.setNumberOfWins(rs.getInt(4));
                teamScore.setNumberOfLoss(rs.getInt(5));
                teamScore.setNumberOfTies(rs.getInt(6));
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }
}
