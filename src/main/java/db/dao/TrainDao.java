package db.dao;

import db.data.ITrainingFactory;
import simulation.model.Training;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainDao implements ITrainingFactory {

    @Override
    public int addTraining(Training training) throws Exception {
        if (training == null) {
            return -1;
        }
        String addTrainingProcedureName = "AddTraining";
        ICallDB callDB = null;
        try {
            callDB = new CallDB(addTrainingProcedureName);
            callDB.setInputParameterInt(1, training.getDaysUntilStatIncreaseCheck());
            callDB.setInputParameterInt(2, training.getLeagueId());

            callDB.setOutputParameterInt(3);
            callDB.execute();
            training.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return training.getId();
    }

    @Override
    public void loadTrainingByLeagueId(int leagueId, Training training) throws Exception {
        if (training == null) {
            return;
        }
        ICallDB callDB = null;
        String loadTrainingByLeagueId = "LoadTrainingByLeagueId(?)";
        try {
            callDB = new CallDB(loadTrainingByLeagueId);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                training = new Training();
                training.setId(rs.getInt(1));
                training.setDaysUntilStatIncreaseCheck(rs.getInt(2));
                training.setLeagueId(rs.getInt(3));
            }
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
    }
}
