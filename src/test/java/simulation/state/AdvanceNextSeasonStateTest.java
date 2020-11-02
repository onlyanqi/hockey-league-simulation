package simulation.state;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.UserMock;
import simulation.model.User;
import validator.Validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdvanceNextSeasonStateTest {

    private static ILeagueFactory leagueFactory;
    private static ITeamFactory teamFactory;
    private static IPlayerFactory playerFactory;
    private static ITradeOfferFactory tradeOfferFactory;
    private static ITradingFactory tradingFactory;
    private static IUserFactory userFactory;
    private static Validation validation;
    private static HockeyContext hockeyContext;


    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContext = new HockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void actionTest(){
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
