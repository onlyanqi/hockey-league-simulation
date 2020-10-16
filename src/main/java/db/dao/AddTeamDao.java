package dao;

import common.Constants;
import simulation.data.IAddTeamFactory;
import simulation.model.Team;

import java.sql.SQLException;

public class AddTeamDao implements IAddTeamFactory {
    @Override
    public int addTeam(Team team) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.addTeam);
            callDB.setInputParameterString(1, team.getName());
            callDB.setInputParameterInt(2, team.getDivisionId());
            callDB.setInputParameterString(3, team.getHeadCoach());
            callDB.setInputParameterString(4, team.getGeneralManager());
            callDB.setOutputParameterInt(5);
            callDB.execute();
            team.setId(callDB.returnOutputParameterInt(5));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return team.getId();
    }

}
