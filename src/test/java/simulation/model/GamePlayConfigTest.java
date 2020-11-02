package simulation.model;

import db.data.IGamePlayConfigFactory;
import db.data.ILeagueFactory;
import db.data.ITradingFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.GamePlayConfigMock;
import simulation.mock.LeagueMock;
import simulation.mock.TradingMock;
import static org.junit.Assert.*;

public class GamePlayConfigTest {

    private static ITradingFactory tradingFactory;
    private static IGamePlayConfigFactory gamePlayConfigFactory;
    private static ILeagueFactory leagueFactory;

    @BeforeClass
    public static void init() {
        tradingFactory = new TradingMock();
        gamePlayConfigFactory = new GamePlayConfigMock();
        leagueFactory = new LeagueMock();
    }

    @Test
    public void getTradingTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        GamePlayConfig gamePlayConfig = new GamePlayConfig(1, gamePlayConfigFactory);
        assertEquals(trading.getId(), gamePlayConfig.getTrading().getId());
        assertEquals(trading.getMaxPlayersPerTrade(), gamePlayConfig.getTrading().getMaxPlayersPerTrade());
    }

    @Test
    public void setTradingTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        GamePlayConfig gamePlayConfig = new GamePlayConfig();
        gamePlayConfig.setTrading(trading);
        assertEquals(1, gamePlayConfig.getTrading().getId());
        assertEquals(3, gamePlayConfig.getTrading().getMaxPlayersPerTrade());
        assertNotEquals(2, gamePlayConfig.getTrading().getId());
    }

    @Test
    public void loadTradingDetailsByLeagueId() throws Exception {
        GamePlayConfig gamePlayConfig = new GamePlayConfig();
        gamePlayConfig.setId(1);
        gamePlayConfig.loadTradingDetailsByLeagueId(tradingFactory);
        assertNotNull(gamePlayConfig.getTrading());
        assertEquals(1, gamePlayConfig.getTrading().getId());
        assertEquals(3, gamePlayConfig.getTrading().getMaxPlayersPerTrade());
        assertNotEquals(2, gamePlayConfig.getTrading().getId());
        assertNotEquals(2, gamePlayConfig.getTrading().getMaxPlayersPerTrade());
    }

    @Test
    public void getLeagueTest() {
        GamePlayConfig state = new GamePlayConfig();
        state.setLeagueId(1);
        assertEquals(1, state.getLeagueId());
    }

    @Test
    public void setLeagueTest() {
        GamePlayConfig state = new GamePlayConfig();
        state.setLeagueId(2);

        assertEquals(2, state.getLeagueId());
    }

}
