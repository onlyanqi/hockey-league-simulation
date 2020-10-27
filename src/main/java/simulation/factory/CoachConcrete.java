package simulation.factory;

import simulation.model.Coach;

public class CoachConcrete {
    public Coach newCoach() {
        return new Coach();
    }
}
