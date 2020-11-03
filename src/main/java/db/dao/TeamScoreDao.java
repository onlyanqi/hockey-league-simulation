package db.dao;

import db.data.ITeamScoreFactory;
import simulation.model.TeamScore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamScoreDao extends DBExceptionLog implements ITeamScoreFactory {
    @Override
    public long addTeamScore(int leagueId, int regularSeason, TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddTeamStanding(?,?,?,?,?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            callDB.setInputParameterDouble(2, teamScore.getPoints());
            callDB.setInputParameterString(3, teamScore.getTeamName());
            callDB.setInputParameterInt(4, teamScore.getNumberOfWins());
            callDB.setInputParameterInt(5, teamScore.getNumberOfLoss());
            callDB.setInputParameterInt(6, teamScore.getNumberOfTies());
            callDB.setInputParameterInt(7, regularSeason);
            callDB.setOutputParameterInt(8);
            callDB.execute();
            teamScore.setId(callDB.returnOutputParameterInt(8));

        } catch (SQLException sqlException) {
            printLog("TeamScoreDao: addTeamScore: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return teamScore.getId();
    }

    @Override
    public void loadTeamScoreById(int id, TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadTeamStandingById(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();
            if (getValidation().isNotNull(rs)) {
                teamScore.setId(rs.getInt(1));
                teamScore.setPoints(rs.getInt(2));
                teamScore.setTeamName(rs.getString(3));
                teamScore.setNumberOfWins(rs.getInt(4));
                teamScore.setNumberOfLoss(rs.getInt(5));
                teamScore.setNumberOfTies(rs.getInt(6));
            }
        } catch (SQLException sqlException) {
            printLog("TeamScoreDao: loadTeamScoreById: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

    @Override
    public List<TeamScore> loadRegularTeamScoreListByLeagueId(int leagueId) throws Exception {
        List<TeamScore> teamScoreList = null;
        ICallDB callDB = null;
        try {
            String procedureName = "LoadRegularTeamStandingByLeagueId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (getValidation().isNotNull(rs)) {
                teamScoreList = new ArrayList<>();
                while (rs.next()) {
                    TeamScore teamScore = new TeamScore();
                    teamScore.setId(rs.getInt(1));
                    teamScore.setPoints(rs.getInt(2));
                    teamScore.setTeamName(rs.getString(3));
                    teamScore.setNumberOfWins(rs.getInt(4));
                    teamScore.setNumberOfLoss(rs.getInt(5));
                    teamScore.setNumberOfTies(rs.getInt(6));
                    teamScoreList.add(teamScore);
                }
            }
        } catch (SQLException sqlException) {
            printLog("TeamScoreDao: loadRegularTeamScoreListByLeagueId: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return teamScoreList;
    }

    @Override
    public List<TeamScore> loadPlayoffTeamScoreListByLeagueId(int leagueId) throws Exception {
        List<TeamScore> teamScoreList = null;
        ICallDB callDB = null;
        try {
            String procedureName = "LoadPlayOffTeamStandingByLeagueId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (getValidation().isNotNull(rs)) {
                teamScoreList = new ArrayList<>();
                while (rs.next()) {
                    TeamScore teamScore = new TeamScore();
                    teamScore.setId(rs.getInt(1));
                    teamScore.setPoints(rs.getInt(2));
                    teamScore.setTeamName(rs.getString(3));
                    teamScore.setNumberOfWins(rs.getInt(4));
                    teamScore.setNumberOfLoss(rs.getInt(5));
                    teamScore.setNumberOfTies(rs.getInt(6));
                    teamScoreList.add(teamScore);
                }
            }
        } catch (SQLException sqlException) {
            printLog("TeamScoreDao: loadPlayoffTeamScoreListByLeagueId: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return teamScoreList;
    }

    @Override
    public void updateTeamScoreById(TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "UpdateTeamStanding(?,?,?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterDouble(1, teamScore.getPoints());
            callDB.setInputParameterString(2, teamScore.getTeamName());
            callDB.setInputParameterInt(3, teamScore.getNumberOfWins());
            callDB.setInputParameterInt(4, teamScore.getNumberOfLoss());
            callDB.setInputParameterInt(5, teamScore.getNumberOfTies());
            callDB.setInputParameterInt(6, teamScore.getId());
            callDB.execute();
        } catch (SQLException sqlException) {
            printLog("TeamScoreDao: updateTeamScoreById: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

}
