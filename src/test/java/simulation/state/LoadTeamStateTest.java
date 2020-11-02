package simulation.state;

import db.data.ILeagueFactory;
import db.data.ITradingFactory;
import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.mock.TradingMock;
import simulation.mock.UserMock;
import simulation.model.GamePlayConfig;
import simulation.model.League;
import simulation.model.Trading;
import simulation.model.User;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoadTeamStateTest {

    private static HockeyContext hockeyContext;
    private static IUserFactory userFactory;
    private static ITradingFactory tradingFactory;
    private static ILeagueFactory leagueFactory;

    @BeforeClass
    public static void init() throws Exception {
        hockeyContext = new HockeyContext();
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

    @Test
    public void updateTradingDetailsToLeagueTest() throws Exception {
        GamePlayConfig gamePlayConfig = new GamePlayConfig();
        League league = new League();
        league.setGamePlayConfig(gamePlayConfig);
        LoadTeamState loadTeamState = new LoadTeamState(hockeyContext);
        loadTeamState.setLeague(league);
        loadTeamState.updateTradingDetailsToLeague(tradingFactory);
        Trading trading = loadTeamState.getLeague().getGamePlayConfig().getTrading();
        assertNotNull(trading);
        assertEquals(trading.getId(), 1);
        assertEquals(trading.getMaxPlayersPerTrade(), 3);
    }

}
