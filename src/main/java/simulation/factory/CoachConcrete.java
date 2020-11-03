package simulation.factory;

import db.dao.CoachDao;
import db.data.ICoachFactory;
import simulation.model.Coach;

public class CoachConcrete {
    public Coach newCoach() {
        return new Coach();
    }

    public ICoachFactory newCoachFactory() {
        return new CoachDao();
    }
}
