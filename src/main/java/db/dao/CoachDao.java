package db.dao;

import db.data.ICoachFactory;
import simulation.model.Coach;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachDao extends DBExceptionLog implements ICoachFactory {
    @Override
    public int addCoach(Coach coach) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddCoach(?,?,?,?,?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, coach.getTeamId());
            callDB.setInputParameterString(2, coach.getName());
            callDB.setInputParameterDouble(3, coach.getSkating());
            callDB.setInputParameterDouble(4, coach.getShooting());
            callDB.setInputParameterDouble(5, coach.getChecking());
            callDB.setInputParameterDouble(6, coach.getSaving());
            callDB.setInputParameterInt(7, coach.getLeagueId());

            callDB.setOutputParameterInt(8);
            callDB.execute();
            coach.setId(callDB.returnOutputParameterInt(8));

        } catch (SQLException sqlException) {
            printLog("CoachDao: addCoach: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return coach.getId();
    }

    @Override
    public void loadCoachById(int id, Coach coach) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadCoachById(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();

            if (getValidation().isNotNull(rs)) {
                coach = new Coach();
                coach.setId(rs.getInt(1));
                coach.setTeamId(rs.getInt(2));
                coach.setName(rs.getString(3));
                coach.setSkating(rs.getDouble(4));
                coach.setShooting(rs.getDouble(5));
                coach.setChecking(rs.getDouble(6));
                coach.setSaving(rs.getDouble(7));
                coach.setLeagueId(rs.getInt(8));
            }

        } catch (SQLException sqlException) {
            printLog("CoachDao: loadCoachById: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

    @Override
    public Coach loadCoachByTeamId(int teamId) throws Exception {
        Coach coach = null;
        ICallDB callDB = null;
        try {
            String procedureName = "LoadCoachByTeamId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, teamId);
            ResultSet rs = callDB.executeLoad();

            if (getValidation().isNotNull(rs)) {
                coach = new Coach();
                coach.setId(rs.getInt(1));
                coach.setTeamId(rs.getInt(2));
                coach.setName(rs.getString(3));
                coach.setSkating(rs.getDouble(4));
                coach.setShooting(rs.getDouble(5));
                coach.setChecking(rs.getDouble(6));
                coach.setSaving(rs.getDouble(7));
                coach.setLeagueId(rs.getInt(8));
            }

        } catch (SQLException sqlException) {
            printLog("CoachDao: loadCoachByTeamId: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return coach;
    }


    @Override
    public List<Coach> loadFreeCoachListByLeagueId(int leagueId) throws Exception {
        List<Coach> freeCoachList = null;
        ICallDB callDB = null;
        try {
            String procedureName = "LoadCoachListByLeagueId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();

            if (getValidation().isNotNull(rs)) {
                freeCoachList = new ArrayList<>();
                while (rs.next()) {
                    Coach coach = new Coach();
                    coach.setId(rs.getInt(1));
                    coach.setTeamId(rs.getInt(2));
                    coach.setName(rs.getString(3));
                    coach.setSkating(rs.getDouble(4));
                    coach.setShooting(rs.getDouble(5));
                    coach.setChecking(rs.getDouble(6));
                    coach.setSaving(rs.getDouble(7));
                    coach.setLeagueId(rs.getInt(8));
                    freeCoachList.add(coach);
                }

            }

        } catch (SQLException sqlException) {
            printLog("CoachDao: loadFreeCoachListByLeagueId: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (getValidation().isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return freeCoachList;
    }
}
