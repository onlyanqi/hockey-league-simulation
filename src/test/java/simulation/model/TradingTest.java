package simulation.model;

import simulation.dao.DaoFactoryMock;
import simulation.dao.IDaoFactory;
import simulation.dao.ITradingDao;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class TradingTest {

    private static ITradingDao tradingDao;
    private static IModelFactory modelFactory;
    private static IDaoFactory daoFactory;
    int zero = 0;
    int one = 1;
    int nextYear = 2021;
    int yearInit = 1900;
    double decimalFive = 0.05f;
    double oneFive = 1.05f;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        tradingDao = daoFactory.newTradingDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void tradingTest() {
        ITrading trading = modelFactory.newTrading();
        assertNotEquals(trading.getId(), zero);
    }

    @Test
    public void tradingFactoryTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertEquals(trading.getId(), 1);
        assertEquals(trading.getLeagueId(), 1);
    }

    @Test
    public void getCurrentYearSeasonMonthsTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertTrue(trading.getCurrentYearSeasonMonths().contains(9));
        assertFalse(trading.getCurrentYearSeasonMonths().contains(5));
    }

    @Test
    public void setCurrentYearSeasonMonthsTest() {
        ITrading trading = modelFactory.newTrading();
        List<Integer> currentYearSeasonMonths = new ArrayList<>(Arrays.asList(9, 10, 11));
        trading.setCurrentYearSeasonMonths(currentYearSeasonMonths);
        assertTrue(trading.getCurrentYearSeasonMonths().contains(9));
        assertFalse(trading.getCurrentYearSeasonMonths().contains(5));
    }

    @Test
    public void getNextYearSeasonMonthsTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertTrue(trading.getNextYearSeasonMonths().contains(zero));
        assertFalse(trading.getNextYearSeasonMonths().contains(6));
    }

    @Test
    public void setNextYearSeasonMonthsTest() {
        ITrading trading = modelFactory.newTrading();
        List<Integer> nextYearSeasonMonths = new ArrayList<>(Arrays.asList(zero, one));
        trading.setNextYearSeasonMonths(nextYearSeasonMonths);
        assertTrue(trading.getNextYearSeasonMonths().contains(zero));
        assertFalse(trading.getNextYearSeasonMonths().contains(7));
    }

    @Test
    public void isLeagueInTradingPeriodTest() {
        ITrading trading = modelFactory.newTrading();
        trading.isLeagueInTradingPeriod(new Date());
        assertTrue(trading.isTradingPeriod());
    }

    @Test
    public void getTradeStartDateTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        Date tradeStartDate = new Date((2020 - yearInit), 9, one);
        int compare = tradeStartDate.compareTo(trading.getTradeStartDate());
        assertEquals(compare, zero);
        assertNotEquals(compare, one);
    }

    @Test
    public void setTradeStartDateTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        Date startDate = new Date((2020 - yearInit), 9, one);
        int compare = trading.getTradeStartDate().compareTo(startDate);
        assertEquals(compare, zero);
        assertNotEquals(compare, one);
    }

    @Test
    public void getTradeEndDateTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        Date endDate = new Date((nextYear - yearInit), one, 22);
        int compare = trading.getTradeEndDate().compareTo(endDate);
        assertEquals(compare, zero);
        assertNotEquals(compare, one);
    }

    @Test
    public void setTradeEndDateTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        Date endDate = new Date((nextYear - yearInit), one, 22);
        int compare = trading.getTradeEndDate().compareTo(endDate);
        assertEquals(compare, zero);
        assertNotEquals(compare, one);
    }

    @Test
    public void calTradeEndDateFromLeagueDateTest() {
        ITrading trading = modelFactory.newTrading();
        trading.calTradeEndDateFromLeagueDate(new Date(System.currentTimeMillis()));
        Date endDate = new Date((nextYear - yearInit), one, 22, 23, 59, 59);
        int compare = trading.getTradeEndDate().compareTo(endDate);
        assertEquals(compare, zero);
        assertNotEquals(compare, one);
    }

    @Test
    public void calTradeStartDateFromLeagueDateTest() {
        ITrading trading = modelFactory.newTrading();
        trading.calTradeStartDateFromLeagueDate(new Date(System.currentTimeMillis()));
        Date tradeStartDate = new Date((2020-yearInit), 9, one);
        int compare = trading.getTradeStartDate().compareTo(tradeStartDate);
        assertEquals(compare, zero);
        assertNotEquals(compare, one);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertEquals(trading.getLeagueId(), one);
        assertNotEquals(trading.getLeagueId(), 2);
    }

    @Test
    public void setLeagueIdTest() {
        ITrading trading = modelFactory.newTrading();
        trading.setLeagueId(1);
        assertEquals(trading.getLeagueId(), one);
        assertNotEquals(trading.getLeagueId(), 2);
    }

    @Test
    public void getLossPointTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertEquals(trading.getLossPoint(), 2);
        assertNotEquals(trading.getLossPoint(), 3);
    }

    @Test
    public void setLossPointTest() {
        ITrading trading = modelFactory.newTrading();
        trading.setLossPoint(one);
        assertEquals(trading.getLossPoint(), one);
        assertNotEquals(trading.getLossPoint(), 2);
    }

    @Test
    public void getRandomTradeOfferChanceTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertTrue(trading.getRandomTradeOfferChance() == decimalFive);
        assertFalse(trading.getRandomTradeOfferChance() == oneFive);
    }

    @Test
    public void setRandomTradeOfferChanceTest() {
        ITrading trading = modelFactory.newTrading();
        trading.setRandomTradeOfferChance(decimalFive);
        assertTrue(trading.getRandomTradeOfferChance() == decimalFive);
        assertFalse(trading.getRandomTradeOfferChance() == oneFive);
    }

    @Test
    public void getMaxPlayersPerTradeTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertEquals(trading.getMaxPlayersPerTrade(), 3);
        assertNotEquals(trading.getMaxPlayersPerTrade(), 5);
    }

    @Test
    public void setMaxPlayersPerTradeTest() {
        ITrading trading = modelFactory.newTrading();
        trading.setMaxPlayersPerTrade(3);
        assertEquals(trading.getMaxPlayersPerTrade(), 3);
        assertNotEquals(trading.getMaxPlayersPerTrade(), 5);
    }

    @Test
    public void getRandomAcceptanceChanceTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertTrue(trading.getRandomAcceptanceChance() == decimalFive);
        assertFalse(trading.getRandomAcceptanceChance() == oneFive);
    }

    @Test
    public void setRandomAcceptanceChanceTest() {
        ITrading trading = modelFactory.newTrading();
        trading.setRandomAcceptanceChance(decimalFive);
        assertTrue(trading.getRandomAcceptanceChance() == decimalFive);
        assertFalse(trading.getRandomAcceptanceChance() == oneFive);
    }

    @Test
    public void getGmTableTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertTrue(trading.getGmTable().get("shrewd") == -0.1);
        assertFalse(trading.getGmTable().get("shrewd") == 0.1);
        assertTrue(trading.getGmTable().get("gambler") == 0.1);
        assertFalse(trading.getGmTable().get("gambler") == -0.1);
    }

    @Test
    public void setGmTableTest() {
        ITrading trading = modelFactory.newTrading();
        Map<String, Double> gmTable = new HashMap<>();
        gmTable.put("shrewd", -0.1);
        gmTable.put("gambler", 0.1);
        gmTable.put("normal", 0.0);
        trading.setGmTable(gmTable);
        assertTrue(trading.getGmTable().get("shrewd") == -0.1);
        assertFalse(trading.getGmTable().get("shrewd") == 0.1);
        assertTrue(trading.getGmTable().get("gambler") == 0.1);
        assertFalse(trading.getGmTable().get("gambler") == -0.1);
    }

    @Test
    public void isTradingPeriodTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(one, tradingDao);
        assertTrue(trading.isTradingPeriod());
    }

    @Test
    public void setTradingPeriodTest() {
        ITrading trading = modelFactory.newTrading();
        assertFalse(trading.isTradingPeriod());
        trading.setTradingPeriod(true);
        assertTrue(trading.isTradingPeriod());
    }

    @Test
    public void addTradingTest() throws Exception {
        ITrading trading = modelFactory.newTradingWithIdDao(1, tradingDao);
        trading.addTrading(tradingDao);
        assertEquals(one, trading.getId());
        assertEquals(3, trading.getMaxPlayersPerTrade());
    }



}
