package simulation.model;

import db.data.IGamePlayConfigFactory;
import db.data.ITradingFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.GamePlayConfigMock;
import simulation.mock.TradingMock;

import static org.junit.Assert.*;

public class GamePlayConfigTest {

    private static ITradingFactory tradingFactory;
    private static IGamePlayConfigFactory gamePlayConfigFactory;

    @BeforeClass
    public static void init() {
        tradingFactory = new TradingMock();
        gamePlayConfigFactory = new GamePlayConfigMock();
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

}
