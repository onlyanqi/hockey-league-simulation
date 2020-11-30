package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IPlayerDao;
import persistance.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.UserMock;
import simulation.model.IAging;
import simulation.model.ILeague;
import simulation.model.IUser;
import simulation.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AdvanceNextSeasonStateTest {

    private static IPlayerDao playerFactory;
    private static IUserDao userFactory;
    private static ILeague league;
    private static IUser user;
    private static IAging aging;
    private static IHockeyContext hockeyContext;
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
        beforeDate = LocalDate.of(2021, 7, 15);
    }

    public AdvanceNextSeasonState newStateWithHockeyContext(IHockeyContext hockeyContext, LocalDate beforeDate) {
        return new AdvanceNextSeasonState(hockeyContext, beforeDate);
    }

    @Test
    public void actionTest() {
        assertEquals(league.getCurrentDate(), LocalDate.of(2021, 6, 15));
        AdvanceNextSeasonState state = newStateWithHockeyContext(hockeyContext, beforeDate);
        List<String> draftPicks = league.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getDraftPicks();
        assertEquals(draftPicks.get(0), "team3");
        state.action();
        draftPicks = league.getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getDraftPicks();
        assertNull(draftPicks.get(0));
        assertEquals(league.getCurrentDate(), LocalDate.of(2021, 9, 29));

    }

    @Test
    public void exitTest() {
        AdvanceNextSeasonState state = newStateWithHockeyContext(hockeyContext, beforeDate);
        assertTrue(state.exit() instanceof PersistState);
        assertNotNull(state.exit());
    }


}
