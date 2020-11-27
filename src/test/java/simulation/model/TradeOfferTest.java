package simulation.model;

import simulation.dao.DaoFactoryMock;
import simulation.dao.IDaoFactory;
import simulation.dao.ITradeOfferDao;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TradeOfferTest {

    private static IDaoFactory daoFactory;
    private static IModelFactory modelFactory;
    private static ITradeOfferDao tradeOfferDao;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        tradeOfferDao = daoFactory.newTradeOfferDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void defaultConstructorTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        assertNotEquals(tradeOffer.getId(), 0);
        assertNotEquals(tradeOffer.getId(), 1);
    }

    @Test
    public void tradeOfferFactoryTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getId(), 1);
        assertEquals(tradeOffer.getFromTeamId(), 1);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getLeagueId(), 1);
        assertNotEquals(tradeOffer.getLeagueId(), 2);
    }

    @Test
    public void setLeagueIdTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setLeagueId(1);
        assertEquals(tradeOffer.getLeagueId(), 1);
        assertNotEquals(tradeOffer.getLeagueId(), 2);
    }

    @Test
    public void getTradingIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getTradingId(), 1);
        assertNotEquals(tradeOffer.getTradingId(), 2);
    }

    @Test
    public void setTradingIdTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setTradingId(1);
        assertEquals(tradeOffer.getTradingId(), 1);
        assertNotEquals(tradeOffer.getTradingId(), 2);
    }

    @Test
    public void getFromTeamIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getFromTeamId(), 1);
        assertNotEquals(tradeOffer.getFromTeamId(), 2);
    }

    @Test
    public void setFromTeamIdTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setFromTeamId(1);
        assertEquals(tradeOffer.getFromTeamId(), 1);
        assertNotEquals(tradeOffer.getFromTeamId(), 2);
    }

    @Test
    public void getToTeamIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getToTeamId(), 3);
        assertNotEquals(tradeOffer.getToTeamId(), 2);
    }

    @Test
    public void setToTeamIdTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setToTeamId(1);
        assertEquals(tradeOffer.getToTeamId(), 1);
        assertNotEquals(tradeOffer.getToTeamId(), 2);
    }

    @Test
    public void getFromPlayerIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getFromPlayerId(), 3);
        assertNotEquals(tradeOffer.getFromPlayerId(), 2);
    }

    @Test
    public void setFromPlayerIdTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setFromPlayerId(1);
        assertEquals(tradeOffer.getFromPlayerId(), 1);
        assertNotEquals(tradeOffer.getFromPlayerId(), 2);
    }

    @Test
    public void getToPlayerIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getToPlayerId(), 3);
        assertNotEquals(tradeOffer.getToPlayerId(), 4);
    }

    @Test
    public void setToPlayerIdTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setToPlayerId(1);
        assertEquals(tradeOffer.getToPlayerId(), 1);
        assertNotEquals(tradeOffer.getToPlayerId(), 4);
    }

    @Test
    public void getSeasonIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getSeasonId(), 1);
        assertNotEquals(tradeOffer.getSeasonId(), 2);
    }

    @Test
    public void setSeasonIdTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setSeasonId(1);
        assertEquals(tradeOffer.getSeasonId(), 1);
        assertNotEquals(tradeOffer.getSeasonId(), 2);
    }

    @Test
    public void getStatusTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getStatus(), "pending");
        assertNotEquals(tradeOffer.getStatus(), 2);
    }

    @Test
    public void setStatusTest() {
        ITradeOffer tradeOffer = modelFactory.newTradeOffer();
        tradeOffer.setStatus("pending");
        assertEquals(tradeOffer.getStatus(), "pending");
        assertNotEquals(tradeOffer.getStatus(), "2");
    }

    @Test
    public void addTradeOfferTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.newTradeOfferWithIdDao(1, tradeOfferDao);
        tradeOffer.addTradeOffer(tradeOfferDao);
        assertEquals(1, tradeOffer.getId());
        assertEquals(1, tradeOffer.getFromTeamId());
    }

}
