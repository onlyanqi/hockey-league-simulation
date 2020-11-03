package simulation.state;

import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.UserMock;
import simulation.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InitializeSeasonStateTest {

    private static IUserFactory userFactory;
    private static HockeyContext hockeyContext;


    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContext = new HockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void actionTest() {
        InitializeSeasonState state = new InitializeSeasonState(hockeyContext);
        assertTrue(state.action() instanceof ISimulateState);
        assertFalse(state.action() instanceof InitializeSeasonState);
    }

    @Test
    public void InitializeRegularSeasonTest() {
        InitializeSeasonState state = new InitializeSeasonState(hockeyContext);
    }

    @Test
    public void exitTest() {
        InitializeSeasonState state = new InitializeSeasonState(hockeyContext);
        assertTrue(state.exit() instanceof AdvanceTimeState);
        assertTrue(state.exit() instanceof ISimulateState);
    }

}
