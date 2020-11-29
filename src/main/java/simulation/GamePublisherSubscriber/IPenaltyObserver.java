package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;

public interface IPenaltyObserver {
    void updatePenalty(ILeague league, String team, Integer count);
}
