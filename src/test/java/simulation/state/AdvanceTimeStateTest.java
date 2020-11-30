package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IEventDao;
import persistance.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.NHLEventMock;
import simulation.mock.UserMock;
import simulation.model.User;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdvanceTimeStateTest {

    private static IUserDao userFactory;
    private static IHockeyContext hockeyContext;
    private static IEventDao eventFactory;


    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
        eventFactory = new NHLEventMock();
    }

    @Test
    public void actionTest() {
        AdvanceTimeState state = new AdvanceTimeState(hockeyContext);
        assertTrue(state.action() instanceof ISimulateState);
        assertFalse(state.action() instanceof AdvanceTimeState);

        LocalDate date = LocalDate.of(2020, Month.OCTOBER, 19);
        LocalDate advancedDate = LocalDate.of(2020, Month.OCTOBER, 20);
        hockeyContext.getUser().getLeague().setCurrentDate(date);
        state.action();
        assertTrue(advancedDate.equals(hockeyContext.getUser().getLeague().getCurrentDate()));

    }

    @Test
    public void exitTest() {
        ExecuteTradeState state = new ExecuteTradeState(hockeyContext);
        assertTrue(state.exit() instanceof AgingState);
        assertTrue(state.exit() instanceof ISimulateState);
    }
}
