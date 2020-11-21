package simulation.factory;

import simulation.model.ITraining;
import simulation.model.Training;

public interface ITrainingFactory {

    ITraining newTraining();

}
