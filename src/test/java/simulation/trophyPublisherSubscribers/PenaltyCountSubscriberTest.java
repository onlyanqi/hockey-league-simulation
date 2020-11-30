package simulation.trophyPublisherSubscribers;

import org.junit.Test;
import persistance.dao.IPlayerDao;
import simulation.mock.PlayerMock;
import simulation.model.IPlayer;
import simulation.model.Player;
import simulation.trophyPublisherSubsribers.PenaltyCountSubscriber;

import static org.junit.Assert.assertEquals;

public class PenaltyCountSubscriberTest {
    @Test
    public void updateTest() throws Exception {
        IPlayerDao playerDao = new PlayerMock();
        IPlayer player = new Player(1, playerDao);
        int oldPenalty = player.getPenaltyCount();
        PenaltyCountSubscriber penaltyCountSubscriber = new PenaltyCountSubscriber();
        penaltyCountSubscriber.update(player, 1);
        int newPenalty = player.getPenaltyCount();
        assertEquals(oldPenalty + 1, newPenalty);
    }
}
