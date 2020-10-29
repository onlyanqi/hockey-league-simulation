package simulation.model;

import db.data.ITradingFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.TradingMock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TradingTest {

    private static ITradingFactory tradingFactory;

    @BeforeClass
    public static void setFactoryObj(){
        tradingFactory = new TradingMock();
    }

    @Test
    public void defaultConstructorTest(){
        Trading trading = new Trading();
        assertEquals(trading.getId(), 0);
    }

    @Test
    public void tradingFactoryTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertEquals(trading.getId(), 1);
        assertEquals(trading.getLeagueId(), 1);
    }

    @Test
    public void getCurrentYearSeasonMonthsTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertTrue(trading.getCurrentYearSeasonMonths().contains(9));
        assertFalse(trading.getCurrentYearSeasonMonths().contains(5));
    }

    @Test
    public void setCurrentYearSeasonMonthsTest() {
        Trading trading = new Trading();
        List<Integer> currentYearSeasonMonths = new ArrayList<>(Arrays.asList(9, 10, 11));
        trading.setCurrentYearSeasonMonths(currentYearSeasonMonths);
        assertTrue(trading.getCurrentYearSeasonMonths().contains(9));
        assertFalse(trading.getCurrentYearSeasonMonths().contains(5));
    }

    @Test
    public void getNextYearSeasonMonthsTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertTrue(trading.getNextYearSeasonMonths().contains(0));
        assertFalse(trading.getNextYearSeasonMonths().contains(6));
    }

    @Test
    public void setNextYearSeasonMonthsTest() {
        Trading trading = new Trading();
        List<Integer> nextYearSeasonMonths = new ArrayList<>(Arrays.asList(0, 1));
        trading.setNextYearSeasonMonths(nextYearSeasonMonths);
        assertTrue(trading.getNextYearSeasonMonths().contains(0));
        assertFalse(trading.getNextYearSeasonMonths().contains(7));
    }

    @Test
    public void isLeagueInTradingPeriodTest(){
        Trading trading = new Trading();
        trading.isLeagueInTradingPeriod(new Date());
        assertTrue(trading.isTradingPeriod());
    }

    @Test
    public void getTradeStartDateTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        Date tradeStartDate = new Date((2020-1900), 9, 1);
        int compare = tradeStartDate.compareTo(trading.getTradeStartDate());
        assertEquals(compare, 0);
        assertNotEquals(compare, 1);
    }

    @Test
    public void setTradeStartDateTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        Date startDate = new Date((2020-1900), 9, 1);
        int compare = trading.getTradeStartDate().compareTo(startDate);
        assertEquals(compare, 0);
        assertNotEquals(compare, 1);
    }

    @Test
    public void getTradeEndDateTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        Date endDate = new Date((2021-1900), 1, 22);
        int compare = trading.getTradeEndDate().compareTo(endDate);
        assertEquals(compare, 0);
        assertNotEquals(compare, 1);
    }

    @Test
    public void setTradeEndDateTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        Date endDate = new Date((2021-1900), 1, 22);
        int compare = trading.getTradeEndDate().compareTo(endDate);
        assertEquals(compare, 0);
        assertNotEquals(compare, 1);
    }

    @Test
    public void calTradeEndDateFromLeagueDateTest(){
        Trading trading = new Trading();
        trading.calTradeEndDateFromLeagueDate(new Date(System.currentTimeMillis()));
        Date endDate = new Date((2021-1900),1,22, 23, 59,59);
        int compare = trading.getTradeEndDate().compareTo(endDate);
        assertEquals(compare, 0);
        assertNotEquals(compare, 1);
    }

    @Test
    public void calTradeStartDateFromLeagueDateTest(){
        Trading trading = new Trading();
        trading.calTradeStartDateFromLeagueDate(new Date(System.currentTimeMillis()));
        Date tradeStartDate = new Date((2020-1900),9,1);
        int compare = trading.getTradeStartDate().compareTo(tradeStartDate);
        assertEquals(compare,0);
        assertNotEquals(compare,1);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertEquals(trading.getLeagueId(), 1);
        assertNotEquals(trading.getLeagueId(), 2);
    }

    @Test
    public void setLeagueIdTest() {
        Trading trading = new Trading();
        trading.setLeagueId(1);
        assertEquals(trading.getLeagueId(), 1);
        assertNotEquals(trading.getLeagueId(), 2);
    }

    @Test
    public void getLossPointTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertEquals(trading.getLossPoint(), 2);
        assertNotEquals(trading.getLossPoint(), 3);
    }

    @Test
    public void setLossPointTest() {
        Trading trading = new Trading();
        trading.setLossPoint(1);
        assertEquals(trading.getLossPoint(), 1);
        assertNotEquals(trading.getLossPoint(), 2);
    }

    @Test
    public void getRandomTradeOfferChanceTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertTrue(trading.getRandomTradeOfferChance() == 0.05f);
        assertFalse(trading.getRandomTradeOfferChance() == 1.05f);
    }

    @Test
    public void setRandomTradeOfferChanceTest() {
        Trading trading = new Trading();
        trading.setRandomTradeOfferChance(0.05f);
        assertTrue(trading.getRandomTradeOfferChance() == 0.05f);
        assertFalse(trading.getRandomTradeOfferChance() == 1.05f);
    }

    @Test
    public void getMaxPlayersPerTradeTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertEquals(trading.getMaxPlayersPerTrade(), 3);
        assertNotEquals(trading.getMaxPlayersPerTrade(), 5);
    }

    @Test
    public void setMaxPlayersPerTradeTest() {
        Trading trading = new Trading();
        trading.setMaxPlayersPerTrade(3);
        assertEquals(trading.getMaxPlayersPerTrade(), 3);
        assertNotEquals(trading.getMaxPlayersPerTrade(), 5);
    }

    @Test
    public void getRandomAcceptanceChanceTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertTrue(trading.getRandomAcceptanceChance() == 0.05f);
        assertFalse(trading.getRandomAcceptanceChance() == 1.05f);
    }

    @Test
    public void setRandomAcceptanceChanceTest() {
        Trading trading = new Trading();
        trading.setRandomAcceptanceChance(0.05f);
        assertTrue(trading.getRandomAcceptanceChance() == 0.05f);
        assertFalse(trading.getRandomAcceptanceChance() == 1.05f);
    }

    @Test
    public void isTradingPeriodTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        assertTrue(trading.isTradingPeriod());
    }

    @Test
    public void setTradingPeriodTest() {
        Trading trading = new Trading();
        assertFalse(trading.isTradingPeriod());
        trading.setTradingPeriod(true);
        assertTrue(trading.isTradingPeriod());
    }

    @Test
    public void addTradingTest() throws Exception {
        Trading trading = new Trading(1, tradingFactory);
        trading.addTrading(tradingFactory);
        assertEquals(1, trading.getId());
        assertEquals(3, trading.getMaxPlayersPerTrade());
    }

}
