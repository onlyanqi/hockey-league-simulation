package db.data;

import simulation.model.Training;

public interface ITrainingFactory {
    int addTraining(Training training) throws Exception;

    void loadTrainingByLeagueId(int leagueId, Training training) throws Exception;
}
