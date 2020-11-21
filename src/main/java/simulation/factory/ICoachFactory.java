package simulation.factory;

import simulation.model.Coach;
import simulation.model.ICoach;

public interface ICoachFactory {
    ICoach newCoach();
    ICoach newCoachWithCoach(ICoach coach);
}
