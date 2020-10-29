package simulation.model;

import db.data.ITradeOfferFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TradeOfferTest {

    private static ITradeOfferFactory tradeOfferFactory;

    @BeforeClass
    public static void setFactoryObj(){
        tradeOfferFactory = new TradeOfferMock();
    }

    @Test
    public void defaultConstructorTest(){
        TradeOffer tradeOffer = new TradeOffer();
        assertEquals(tradeOffer.getId(), 0);
    }

    @Test
    public void tradeOfferFactoryTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getId(), 1);
        assertEquals(tradeOffer.getFromTeamId(), 1);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getLeagueId(), 1);
        assertNotEquals(tradeOffer.getLeagueId(), 2);
    }

    @Test
    public void setLeagueIdTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setLeagueId(1);
        assertEquals(tradeOffer.getLeagueId(), 1);
        assertNotEquals(tradeOffer.getLeagueId(), 2);
    }

    @Test
    public void getTradingIdTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getTradingId(), 1);
        assertNotEquals(tradeOffer.getTradingId(), 2);
    }

    @Test
    public void setTradingIdTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setTradingId(1);
        assertEquals(tradeOffer.getTradingId(), 1);
        assertNotEquals(tradeOffer.getTradingId(), 2);
    }

    @Test
    public void getFromTeamIdTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getFromTeamId(), 1);
        assertNotEquals(tradeOffer.getFromTeamId(), 2);
    }

    @Test
    public void setFromTeamIdTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setFromTeamId(1);
        assertEquals(tradeOffer.getFromTeamId(), 1);
        assertNotEquals(tradeOffer.getFromTeamId(), 2);
    }

    @Test
    public void getToTeamIdTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getToTeamId(), 3);
        assertNotEquals(tradeOffer.getToTeamId(), 2);
    }

    @Test
    public void setToTeamIdTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setToTeamId(1);
        assertEquals(tradeOffer.getToTeamId(), 1);
        assertNotEquals(tradeOffer.getToTeamId(), 2);
    }

    @Test
    public void getFromPlayerIdTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getFromPlayerId(), 3);
        assertNotEquals(tradeOffer.getFromPlayerId(), 2);
    }

    @Test
    public void setFromPlayerIdTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setFromPlayerId(1);
        assertEquals(tradeOffer.getFromPlayerId(), 1);
        assertNotEquals(tradeOffer.getFromPlayerId(), 2);
    }

    @Test
    public void getToPlayerIdTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getToPlayerId(), 3);
        assertNotEquals(tradeOffer.getToPlayerId(), 4);
    }

    @Test
    public void setToPlayerIdTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setToPlayerId(1);
        assertEquals(tradeOffer.getToPlayerId(), 1);
        assertNotEquals(tradeOffer.getToPlayerId(), 4);
    }

    @Test
    public void getSeasonIdTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getSeasonId(), 1);
        assertNotEquals(tradeOffer.getSeasonId(), 2);
    }

    @Test
    public void setSeasonIdTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setSeasonId(1);
        assertEquals(tradeOffer.getSeasonId(), 1);
        assertNotEquals(tradeOffer.getSeasonId(), 2);
    }

    @Test
    public void getStatusTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(tradeOffer.getStatus(), "pending");
        assertNotEquals(tradeOffer.getStatus(), 2);
    }

    @Test
    public void setStatusTest() {
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setStatus("pending");
        assertEquals(tradeOffer.getStatus(), "pending");
        assertNotEquals(tradeOffer.getStatus(), "2");
    }

    @Test
    public void addTradeOfferTest() throws Exception {
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        tradeOffer.addTradeOffer(tradeOfferFactory);
        assertEquals(1, tradeOffer.getId());
        assertEquals(1, tradeOffer.getFromTeamId());
    }

}
