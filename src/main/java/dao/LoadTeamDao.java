package dao;

import common.Constants;
import data.ILoadTeamFactory;
import model.Player;
import model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoadTeamDao implements ILoadTeamFactory {

    @Override
    public void loadTeamById(int id, Team team) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadTeamByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();
            team = new Team();
            team.setId(callDB.returnOutputParameterInt(2));
            team.setName(callDB.returnOutputParameterString(3));
            team.setDivisionId(callDB.returnOutputParameterInt(4));

        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public Team loadTeamByName(String teamName) throws Exception {
        ICallDB callDB = null;
        Team team = null;
        try {
            callDB = new CallDB(Constants.loadTeamByName);
            callDB.setInputParameterString(1, teamName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();
            team = new Team();
            team.setId(callDB.returnOutputParameterInt(2));
            team.setName(callDB.returnOutputParameterString(3));
            team.setDivisionId(callDB.returnOutputParameterInt(4));

        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
        return team;
    }

    @Override
    public List<Team> loadTeamListByDivisionId(int divisionId) throws Exception {
        List<Team> teamList = null;
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.loadTeamListByDivisionId);
            callDB.setInputParameterInt(1, divisionId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                teamList = new ArrayList<>();
                while (rs.next()) {
                    Team team = new Team();
                    team.setId(rs.getInt(1));
                    team.setName(rs.getString(2));
                    team.setDivisionId(divisionId);
                    teamList.add(team);
                }
            }
        } catch (Exception e){
            throw e;
        }

        return teamList;
    }
}
