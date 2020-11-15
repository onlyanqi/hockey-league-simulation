package simulation.state;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.UserMock;
import simulation.model.User;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GeneratePlayoffScheduleDateTest {
    private static ILeagueFactory leagueFactory;
    private static ITeamFactory teamFactory;
    private static IPlayerFactory playerFactory;
    private static ITradeOfferFactory tradeOfferFactory;
    private static ITradingFactory tradingFactory;
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
