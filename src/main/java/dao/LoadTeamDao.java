package dao;

import common.Constants;
import data.ILoadTeamFactory;
import model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
