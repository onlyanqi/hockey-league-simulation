package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.UserMock;
import simulation.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GeneratePlayoffScheduleDateTest {

    private static IUserDao userFactory;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void actionTest() {
        GeneratePlayoffScheduleState state = new GeneratePlayoffScheduleState(hockeyContext);
        assertTrue(state.action() instanceof ISimulateState);
        assertFalse(state.action() instanceof GeneratePlayoffScheduleState);
    }

    @Test
    public void exitTest() {
        GeneratePlayoffScheduleState state = new GeneratePlayoffScheduleState(hockeyContext);
        assertTrue(state.exit() instanceof TrainingState);
        assertTrue(state.exit() instanceof ISimulateState);
    }
}
