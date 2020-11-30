package simulation.trophyPublisherSubscribers;

import org.junit.Test;
import simulation.dao.IPlayerDao;
import simulation.mock.PlayerMock;
import simulation.model.IPlayer;
import simulation.model.Player;
import simulation.trophyPublisherSubsribers.GoalScoreSubscriber;

import static org.junit.Assert.assertEquals;

public class GoalScoreSubscriberTest {
    @Test
    public void updateTest() throws Exception {
        IPlayerDao playerDao = new PlayerMock();
        IPlayer player = new Player(1, playerDao);
        int oldScore = player.getGoalScore();
        GoalScoreSubscriber goalScoreSubscriber = new GoalScoreSubscriber();
        goalScoreSubscriber.update(player, 1);
        int newScore = player.getGoalScore();
        assertEquals(oldScore + 1, newScore);
    }
}
