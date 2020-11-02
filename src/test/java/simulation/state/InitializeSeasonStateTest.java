package simulation.state;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.UserMock;
import simulation.model.Game;
import simulation.model.User;
import validator.Validation;

import static org.junit.Assert.*;

public class InitializeSeasonStateTest {

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
        InitializeSeasonState state = new InitializeSeasonState(hockeyContext);
        assertNull(state.action());
    }

    @Test
    public void InitializeRegularSeasonTest(){
        InitializeSeasonState state = new InitializeSeasonState(hockeyContext);
    }

    @Test
    public void exitTest(){
        InitializeSeasonState state = new InitializeSeasonState(hockeyContext);
        assertTrue(state.exit() instanceof AdvanceTimeState);
        assertTrue(state.exit() instanceof ISimulateState);
    }

}
