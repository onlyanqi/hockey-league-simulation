package db.dao;

import db.data.ICoachFactory;
import simulation.model.Coach;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachDao implements ICoachFactory {
    @Override
    public int addCoach(Coach coach) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddCoach(?,?,?,?,?,?,?,?)");
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
            throw sqlException;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
        return coach.getId();
    }

    @Override
    public void loadCoachById(int id, Coach coach) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadCoachById(?)");
            callDB.setInputParameterInt(1, id);
            ResultSet rs = callDB.executeLoad();

            if (rs != null) {
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

        } catch (Exception e) {
            throw e;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
    }

    @Override
    public Coach loadCoachByTeamId(int teamId) throws Exception {
        Coach coach = null;
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadCoachByTeamId(?)");
            callDB.setInputParameterInt(1, teamId);
            ResultSet rs = callDB.executeLoad();

            if (rs != null) {
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

        } catch (Exception e) {
            throw e;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
        return coach;
    }


    @Override
    public List<Coach> loadFreeCoachListByLeagueId(int leagueId) throws Exception {
        List<Coach> freeCoachList = null;
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadCoachListByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();

            if (rs != null) {
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

        } catch (Exception e) {
            throw e;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
        return freeCoachList;
    }
}
