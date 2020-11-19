package simulation.state;

import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.UserMock;
import simulation.model.Training;
import simulation.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InjuryCheckStateTest {
    private static IUserFactory userFactory;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = new HockeyContextConcrete();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
    }


    @Test
    public void exitTest() {
        ExecuteTradeState state = new ExecuteTradeState(hockeyContext);
        assertTrue(state.exit() instanceof ISimulateState);
        assertFalse(state.exit() instanceof Training);
        assertFalse(state.exit() instanceof AdvanceTimeState);
        assertFalse(state.exit() instanceof AdvanceNextSeasonState);
        assertFalse(state.exit() instanceof PersistState);
    }
}
