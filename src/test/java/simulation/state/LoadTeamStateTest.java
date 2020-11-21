package simulation.state;

import db.data.ILeagueDao;
import db.data.ITradingDao;
import db.data.IUserDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.TradingMock;
import simulation.mock.UserMock;
import simulation.model.League;
import simulation.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoadTeamStateTest {

    private static IHockeyContext hockeyContext;
    private static IUserDao userFactory;
    private static ITradingDao tradingFactory;
    private static ILeagueDao leagueFactory;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        userFactory = new UserMock();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
        tradingFactory = new TradingMock();
        leagueFactory = new LeagueMock();
    }

    @Test
    public void getLeagueTest() throws Exception {
        ExecuteTradeState state = new ExecuteTradeState();
        League league = new League(1, leagueFactory);
        state.setLeague(league);
        assertEquals(1, state.getLeague().getId());
        assertEquals(1, state.getLeague().getCreatedBy());
    }

    @Test
    public void setLeagueTest() {
        League league = new League();
        league.setId(1);
        league.setCreatedBy(1);

        ExecuteTradeState state = new ExecuteTradeState();
        state.setLeague(league);

        assertEquals(1, state.getLeague().getId());
        assertEquals(1, state.getLeague().getCreatedBy());
    }

}
