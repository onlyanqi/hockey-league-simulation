package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IDaoFactory;
import persistance.dao.ITradeOfferDao;
import simulation.dao.DaoFactoryMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TradeOfferTest {

    private static IDaoFactory daoFactory;
    private static IModelFactory modelFactory;
    private static ITradeOfferDao tradeOfferDao;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        tradeOfferDao = daoFactory.createTradeOfferDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void tradeOfferTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        assertNotEquals(tradeOffer.getId(), 0);
        assertNotEquals(tradeOffer.getId(), 1);
    }

    @Test
    public void tradeOfferFactoryTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getId(), 1);
        assertEquals(tradeOffer.getFromTeamId(), 1);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getLeagueId(), 1);
        assertNotEquals(tradeOffer.getLeagueId(), 2);
    }

    @Test
    public void setLeagueIdTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setLeagueId(1);
        assertEquals(tradeOffer.getLeagueId(), 1);
        assertNotEquals(tradeOffer.getLeagueId(), 2);
    }

    @Test
    public void getTradingIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getTradingId(), 1);
        assertNotEquals(tradeOffer.getTradingId(), 2);
    }

    @Test
    public void setTradingIdTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setTradingId(1);
        assertEquals(tradeOffer.getTradingId(), 1);
        assertNotEquals(tradeOffer.getTradingId(), 2);
    }

    @Test
    public void getFromTeamIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getFromTeamId(), 1);
        assertNotEquals(tradeOffer.getFromTeamId(), 2);
    }

    @Test
    public void setFromTeamIdTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setFromTeamId(1);
        assertEquals(tradeOffer.getFromTeamId(), 1);
        assertNotEquals(tradeOffer.getFromTeamId(), 2);
    }

    @Test
    public void getToTeamIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getToTeamId(), 3);
        assertNotEquals(tradeOffer.getToTeamId(), 2);
    }

    @Test
    public void setToTeamIdTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setToTeamId(1);
        assertEquals(tradeOffer.getToTeamId(), 1);
        assertNotEquals(tradeOffer.getToTeamId(), 2);
    }

    @Test
    public void getFromPlayerIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getFromPlayerId(), 3);
        assertNotEquals(tradeOffer.getFromPlayerId(), 2);
    }

    @Test
    public void setFromPlayerIdTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setFromPlayerId(1);
        assertEquals(tradeOffer.getFromPlayerId(), 1);
        assertNotEquals(tradeOffer.getFromPlayerId(), 2);
    }

    @Test
    public void getToPlayerIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getToPlayerId(), 3);
        assertNotEquals(tradeOffer.getToPlayerId(), 4);
    }

    @Test
    public void setToPlayerIdTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setToPlayerId(1);
        assertEquals(tradeOffer.getToPlayerId(), 1);
        assertNotEquals(tradeOffer.getToPlayerId(), 4);
    }

    @Test
    public void getSeasonIdTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getSeasonId(), 1);
        assertNotEquals(tradeOffer.getSeasonId(), 2);
    }

    @Test
    public void setSeasonIdTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setSeasonId(1);
        assertEquals(tradeOffer.getSeasonId(), 1);
        assertNotEquals(tradeOffer.getSeasonId(), 2);
    }

    @Test
    public void getStatusTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        assertEquals(tradeOffer.getStatus(), "pending");
        assertNotEquals(tradeOffer.getStatus(), 2);
    }

    @Test
    public void setStatusTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setStatus("pending");
        assertEquals(tradeOffer.getStatus(), "pending");
        assertNotEquals(tradeOffer.getStatus(), "2");
    }

    @Test
    public void addTradeOfferTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        tradeOffer.addTradeOffer(tradeOfferDao);
        assertEquals(1, tradeOffer.getId());
        assertEquals(1, tradeOffer.getFromTeamId());
    }

    @Test
    public void getFromPlayerIdListTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        int fromPlayerId = tradeOffer.getFromPlayerIdList().get(0);
        assertEquals(fromPlayerId, 1);
        assertNotEquals(fromPlayerId, 0);
    }

    @Test
    public void setFromPlayerIdListTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        List<Integer> fromPlayerIdList = new ArrayList<>();
        fromPlayerIdList.add(1);
        tradeOffer.setFromPlayerIdList(fromPlayerIdList);
        int fromPlayerId = tradeOffer.getFromPlayerIdList().get(0);
        assertEquals(fromPlayerId, 1);
        assertNotEquals(fromPlayerId, 2);
    }

    @Test
    public void getToPlayerIdListTest() throws Exception {
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        int toPlayerId = tradeOffer.getToPlayerIdList().get(0);
        assertEquals(toPlayerId, 2);
        assertNotEquals(toPlayerId, 1);
    }

    @Test
    public void setToPlayerIdListTest() {
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        List<Integer> toPlayerIdList = new ArrayList<>();
        toPlayerIdList.add(2);
        tradeOffer.setToPlayerIdList(toPlayerIdList);
        int toPlayerId = tradeOffer.getToPlayerIdList().get(0);
        assertEquals(toPlayerId, 2);
        assertNotEquals(toPlayerId, 1);
    }

}
