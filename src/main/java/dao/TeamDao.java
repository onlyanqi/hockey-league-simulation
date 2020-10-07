package dao;

import common.Constants;
import data.ITeamFactory;
import model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamDao implements ITeamFactory{
    @Override
    public int addTeam(Team team) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.addTeam);
            callDB.setInputParameterString(1, team.getName());
            callDB.setInputParameterInt(2, team.getDivisionId());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            team.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return team.getId();
    }

    @Override
    public void loadTeamByName(int id, Team team) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadTeamByName);
            callDB.setInputParameterString(1, team.getName());
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    team.setId(rs.getInt(2));
                    team.setName(rs.getString(3));
                    team.setDivisionId(rs.getInt(4));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }
}
