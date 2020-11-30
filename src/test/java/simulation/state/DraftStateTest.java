package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.UserMock;
import simulation.model.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class DraftStateTest {

    private static IUserDao userFactory;
    private static IHockeyContext hockeyContext;
    private static ILeague league;
    private static IUser user;
    private static IAging aging;
    private static LocalDate beforeDate;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        user = new User(5, userFactory);
        hockeyContext.setUser(user);
        league = hockeyContext.getUser().getLeague();
        aging = league.getGamePlayConfig().getAging();
        beforeDate = LocalDate.of(2021, 6, 15);
    }

    public DraftState newStateWithHockeyContext(IHockeyContext hockeyContext, LocalDate beforeDate) {
        return new DraftState(hockeyContext, beforeDate);
    }

    @Test
    public void actionTest() {
        assertEquals(league.getDraftedPlayerList().size(), 0);
        assertEquals(league.getCurrentDate(), LocalDate.of(2021, 6, 15));
        DraftState state = newStateWithHockeyContext(hockeyContext, beforeDate);
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        league.setCurrentDate(nhlEvents.getPlayerDraftDate());
        state.generatePlayers(7, 33);
        aging.agingPlayerPeriod(league, beforeDate);
        assertEquals(league.getCurrentDate(), LocalDate.of(2021, 7, 15));
        assertNotEquals(league.getDraftedPlayerList().size(), 0);
    }

    @Test
    public void exitTest() throws Exception {
        DraftState state = newStateWithHockeyContext(hockeyContext, beforeDate);
        assertTrue(state.exit() instanceof AdvanceNextSeasonState);
        assertNotNull(state.exit());
    }


}
