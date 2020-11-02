package simulation.state;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.UserMock;
import simulation.model.User;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdvanceNextSeasonStateTest {

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
    public void actionTest() throws Exception {
        AdvanceNextSeasonState state = new AdvanceNextSeasonState(hockeyContext);
        assertTrue(state.action() instanceof ISimulateState);
        assertFalse(state.action() instanceof AdvanceNextSeasonState);
    }

    @Test
    public void exitTest(){
        AdvanceNextSeasonState state = new AdvanceNextSeasonState(hockeyContext);
        System.out.println(state.exit());
        assertTrue(state.exit() instanceof PersistState);
        assertTrue(state.exit() instanceof ISimulateState);
    }
}
