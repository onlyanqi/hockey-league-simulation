package simulation.factory;

import db.data.ICoachFactory;
import simulation.model.Coach;

public class CoachConcrete {
    public Coach newCoach() {
        return new Coach();
    }

}
