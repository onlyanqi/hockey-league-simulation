package simulation.TrophyPublisherSubsribers;

import simulation.model.Player;

public class GoalScoreSubscriber  implements ITrophyEventListeners{

    @Override
    public void update(Object object, Integer count) {
        Player player = (Player) object;
        player.setGoalScore(player.getGoalScore()+count);
    }
}
