package simulation.factory;

import simulation.model.Training;

public class TrainingConcrete {
    public Training newTraining (){
        return new Training();
    }
}
