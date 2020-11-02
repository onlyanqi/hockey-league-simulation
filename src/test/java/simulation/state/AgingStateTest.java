package simulation.state;

import db.data.ILeagueFactory;
import db.data.IPlayerFactory;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.mock.PlayerMock;
import simulation.model.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AgingStateTest {

    @Test
    public void agingPlayerDayTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        IPlayerFactory playerFactory = new PlayerMock();
        Player player = new Player(12, playerFactory);
        assertTrue(player.getInjured());
        assertNotNull(player.getInjuryStartDate());
        assertEquals(player.getInjuryDatesRange(),80);
        player.agingInjuryRecovery(league);
        assertFalse(player.getInjured());
        assertNull(player.getInjuryStartDate());
        assertEquals(player.getInjuryDatesRange(),0);
    }
}
