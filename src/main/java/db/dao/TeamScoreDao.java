package db.dao;

import db.data.ITeamScoreFactory;
import simulation.model.TeamScore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamScoreDao implements ITeamScoreFactory {
    @Override
    public long addTeamScore(int leagueId, int regularSeason, TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddTeamStanding(?,?,?,?,?,?,?,?)");
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
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return teamScore.getId();
    }

    @Override
    public void loadTeamScoreById(int id, TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadTeamStandingById(?)");
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
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
    public List<TeamScore> loadRegularTeamScoreListByLeagueId(int leagueId) throws Exception {
        List<TeamScore> teamScoreList = null;
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadRegularTeamStandingByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
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
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return teamScoreList;
    }

    @Override
    public List<TeamScore> loadPlayoffTeamScoreListByLeagueId(int leagueId) throws Exception {
        List<TeamScore> teamScoreList = null;
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadPlayOffTeamStandingByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
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
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return teamScoreList;
    }

    @Override
    public void updateTeamScoreById(TeamScore teamScore) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("UpdateTeamStanding(?,?,?,?,?,?)");
            callDB.setInputParameterDouble(1, teamScore.getPoints());
            callDB.setInputParameterString(2, teamScore.getTeamName());
            callDB.setInputParameterInt(3, teamScore.getNumberOfWins());
            callDB.setInputParameterInt(4, teamScore.getNumberOfLoss());
            callDB.setInputParameterInt(5, teamScore.getNumberOfTies());
            callDB.setInputParameterInt(6, teamScore.getId());
            callDB.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
    }

}
