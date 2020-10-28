package db.dao;

import db.data.ICoachFactory;
import simulation.model.Coach;

import java.sql.SQLException;

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
            callDB.closeConnection();
        }
        return coach.getId();
    }

    @Override
    public void loadCoachById(int id, Coach coach) throws Exception {

    }

    @Override
    public Coach loadCoachByLeagueId(int id) throws Exception {
        return null;
    }
}
