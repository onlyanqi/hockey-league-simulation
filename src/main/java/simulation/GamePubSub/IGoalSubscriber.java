package simulation.GamePubSub;

import simulation.model.ILeague;
import simulation.model.IPlayer;
import simulation.model.ITeam;

public interface IGoalSubscriber {
    void updateGoal(ILeague league, String team,Integer count);
}
