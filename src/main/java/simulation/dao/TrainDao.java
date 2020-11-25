package simulation.dao;

import simulation.model.ITraining;

public class TrainDao extends DBExceptionLog implements ITrainingDao {


    @Override
    public int addTraining(ITraining training) throws Exception {
        return 0;
    }

    @Override
    public void loadTrainingByLeagueId(int leagueId, ITraining training) throws Exception {

    }
}
