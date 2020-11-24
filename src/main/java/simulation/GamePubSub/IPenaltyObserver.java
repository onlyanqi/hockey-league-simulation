package simulation.GamePubSub;

import simulation.model.ILeague;

public interface IPenaltyObserver {
    void updatePenalty(ILeague league, String team, Integer count);
}
