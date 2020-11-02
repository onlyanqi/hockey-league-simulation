package db.dao;

import db.data.ITeamFactory;
import simulation.model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDao implements ITeamFactory {
    @Override
    public int addTeam(Team team) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddTeam(?,?,?,?,?,?,?)");
            callDB.setInputParameterString(1, team.getName());
            callDB.setInputParameterInt(2, team.getDivisionId());
            callDB.setInputParameterBoolean(3, team.isAiTeam());
            callDB.setInputParameterInt(4, team.getTradeOfferCountOfSeason());
            callDB.setInputParameterInt(5, team.getLossPoint());
            callDB.setInputParameterDouble(6,team.getStrength());
            callDB.setOutputParameterInt(7);
            callDB.execute();
            team.setId(callDB.returnOutputParameterInt(7));

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return team.getId();
    }

    @Override
    public void loadTeamById(int id, Team team) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadTeamById(?,?,?,?,?,?)");
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.setOutputParameterInt(5);
            callDB.setOutputParameterInt(6);
            callDB.executeLoad();

            team.setId(callDB.returnOutputParameterInt(2));
            team.setName(callDB.returnOutputParameterString(3));
            team.setDivisionId(callDB.returnOutputParameterInt(4));
            team.setTradeOfferCountOfSeason(callDB.returnOutputParameterInt(5));
            team.setLossPoint(callDB.returnOutputParameterInt(6));

        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public void loadTeamByName(String teamName, Team team) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadTeamByName(?,?,?,?,?,?)");
            callDB.setInputParameterString(1, teamName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.setOutputParameterInt(5);
            callDB.setOutputParameterInt(6);
            callDB.executeLoad();

            team.setId(callDB.returnOutputParameterInt(2));
            team.setName(callDB.returnOutputParameterString(3));
            team.setDivisionId(callDB.returnOutputParameterInt(4));
            team.setTradeOfferCountOfSeason(callDB.returnOutputParameterInt(5));
            team.setLossPoint(callDB.returnOutputParameterInt(6));

        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public List<Team> loadTeamListByDivisionId(int divisionId) throws Exception {
        List<Team> teamList = null;
        ICallDB callDB;
        try {
            callDB = new CallDB("LoadTeamListByDivisionId(?)");
            callDB.setInputParameterInt(1, divisionId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                teamList = new ArrayList<>();
                while (rs.next()) {
                    Team team = new Team();
                    team.setId(rs.getInt(1));
                    team.setName(rs.getString(2));
                    team.setDivisionId(divisionId);
                    team.setAiTeam(rs.getBoolean(4));
                    team.setTradeOfferCountOfSeason(rs.getInt(5));
                    team.setLossPoint(rs.getInt(6));
                    teamList.add(team);
                }
            }
        } catch (Exception e) {
            throw e;
        }

        return teamList;
    }

    @Override
    public void updateTeamById(Team team) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("UpdateTeamById(?,?,?,?,?,?,?)");
            callDB.setInputParameterString(1, team.getName());
            callDB.setInputParameterInt(2, team.getDivisionId());
            callDB.setInputParameterBoolean(3, team.isAiTeam());
            callDB.setInputParameterInt(4, team.getTradeOfferCountOfSeason());
            callDB.setInputParameterInt(5, team.getLossPoint());
            callDB.setInputParameterDouble(6,team.getStrength());
            callDB.setInputParameterInt(7,team.getId());
            callDB.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            assert callDB != null;
            callDB.closeConnection();
        }
    }

}
