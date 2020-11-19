package simulation.state;

import db.data.ILeagueFactory;
import db.data.IPlayerFactory;
import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.PlayerMock;
import simulation.mock.UserMock;
import simulation.model.League;
import simulation.model.Player;
import simulation.model.User;

import static org.junit.Assert.*;

public class AgingStateTest {

    private static IUserFactory userFactory;
    private static IHockeyContext hockeyContext;


    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        IHockeyContextFactory hockeyContextFactory = new HockeyContextConcrete();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void agingPlayerDayTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        IPlayerFactory playerFactory = new PlayerMock();
        Player player = new Player(12, playerFactory);
        assertTrue(player.getInjured());
        assertNotNull(player.getInjuryStartDate());
        assertEquals(player.getInjuryDatesRange(), 80);
        player.agingInjuryRecovery(league);
        assertFalse(player.getInjured());
        assertNull(player.getInjuryStartDate());
        assertEquals(player.getInjuryDatesRange(), 0);

    }

    @Test
    public void actionTest() {
        AgingState state = new AgingState(hockeyContext);
        assertTrue(state.action() instanceof ISimulateState);
        assertFalse(state.action() instanceof AgingState);
        assertTrue(state.action() instanceof PersistState);
    }

}
