package simulation.trophyPublisherSubscribers;

import org.junit.Test;
import simulation.dao.IPlayerDao;
import simulation.mock.PlayerMock;
import simulation.model.IPlayer;
import simulation.model.Player;
import simulation.trophyPublisherSubsribers.SavesSubscriber;

import static org.junit.Assert.assertEquals;

public class SavesSubscriberTest {

    @Test
    public void updateTest() throws Exception {
        IPlayerDao playerDao = new PlayerMock();
        IPlayer player = new Player(1, playerDao);
        int oldSaves = player.getSaves();
        SavesSubscriber savesSubscriber = new SavesSubscriber();
        savesSubscriber.update(player, 1);
        int newSaves = player.getSaves();
        assertEquals(oldSaves + 1, newSaves);
    }
}
