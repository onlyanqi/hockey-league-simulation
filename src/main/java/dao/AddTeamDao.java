package dao;

import common.Constants;
import data.IAddTeamFactory;
import data.ILoadTeamFactory;
import model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddTeamDao implements IAddTeamFactory {
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

}
