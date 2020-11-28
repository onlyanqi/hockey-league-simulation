package simulation.GamePublisherSubscriber;

import simulation.model.ILeague;
public interface IGoalSubscriber {
    void updateGoal(ILeague league, String team,Integer count);
}
