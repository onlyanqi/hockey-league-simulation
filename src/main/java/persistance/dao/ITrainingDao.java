package persistance.dao;

import simulation.model.ITraining;

public interface ITrainingDao {

    int addTraining(ITraining training) throws Exception;

    void loadTrainingByLeagueId(int leagueId, ITraining training) throws Exception;
}
