package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.UserMock;
import simulation.model.ILeague;
import simulation.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InjuryCheckStateTest {
    private static IUserDao userFactory;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;
    private static ILeague league;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
        league = hockeyContext.getUser().getLeague();
    }

    public InjuryCheckState newStateWithHockeyContext(IHockeyContext hockeyContext) {
        return new InjuryCheckState(hockeyContext);
    }

    @Test
    public void actionTest() {
        InjuryCheckState state = newStateWithHockeyContext(hockeyContext);
    }


    @Test
    public void exitTest() {
        InjuryCheckState state = newStateWithHockeyContext(hockeyContext);
        assertTrue(state.exit() instanceof ISimulateState);
        assertTrue(state.exit() instanceof AgingState);
        assertFalse(state.exit() instanceof SimulateGameState);
        assertFalse(state.exit() instanceof ExecuteTradeState);
    }
}
