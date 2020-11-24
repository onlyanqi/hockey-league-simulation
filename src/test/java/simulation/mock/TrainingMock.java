package simulation.mock;

import simulation.dao.ITrainingDao;
import simulation.model.ITraining;

public class TrainingMock implements ITrainingDao {

    public ITraining getTraining(ITraining training) {
        training.setId(1);
        training.setLeagueId(1);
        training.setDaysUntilStatIncreaseCheck(50);
        return training;
    }

    @Override
    public int addTraining(ITraining training) throws Exception {
        if (training == null) {
            return -1;
        } else {
            training = getTraining(training);
            return training.getId();
        }
    }

    @Override
    public void loadTrainingByLeagueId(int leagueId, ITraining training) throws Exception {
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
