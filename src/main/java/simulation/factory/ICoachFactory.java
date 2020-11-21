package simulation.factory;

import simulation.model.Coach;

public interface ICoachFactory {
    Coach newCoach();
    Coach newCoachWithCoach(Coach coach);
}
