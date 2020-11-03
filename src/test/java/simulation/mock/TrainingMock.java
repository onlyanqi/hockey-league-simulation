package simulation.mock;

import db.data.ITrainingFactory;
import simulation.model.Training;

public class TrainingMock implements ITrainingFactory {

    public Training getTraining(Training training) {
        training.setId(1);
        training.setLeagueId(1);
        training.setDaysUntilStatIncreaseCheck(50);
        return training;
    }

    @Override
    public int addTraining(Training training) throws Exception {
        if (training == null) {
            return -1;
        } else {
            training = getTraining(training);
            return training.getId();
        }
    }

    @Override
    public void loadTrainingByLeagueId(int leagueId, Training training) throws Exception {
        switch (leagueId) {
            case 1:
                training.setDaysUntilStatIncreaseCheck(50);
                training.setId(1);
                training.setLeagueId(1);
                break;
            case 2:
                training.setDaysUntilStatIncreaseCheck(20);
                training.setId(1);
                training.setLeagueId(1);
        }
    }
}
