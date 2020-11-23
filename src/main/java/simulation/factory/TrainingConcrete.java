package simulation.factory;

import simulation.model.ITraining;
import simulation.model.Training;

public class TrainingConcrete implements ITrainingFactory{
    public ITraining newTraining() {
        return new Training();
    }
}
