package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IEventDao;
import simulation.mock.NHLEventMock;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class NHLEventsTest {

    private static IEventDao iEventDao;

    @BeforeClass
    public static void setFactoryObj() {
        iEventDao = new NHLEventMock();
    }

    @Test
    public void defaultConstructorTest() {
        NHLEvents nhlEvents = new NHLEvents();
        assertNotEquals(nhlEvents.getId(), 0);
    }

    @Test
    public void nhlEventsFactoryTest() throws Exception {
        NHLEvents nhlEvents = new NHLEvents(0, iEventDao);
        assertEquals(nhlEvents.getId(), 3);
        assertTrue(nhlEvents.getRegularSeasonStartDate().equals(LocalDate.of(2020, Month.SEPTEMBER, 30)));

        NHLEvents nhlEvents2 = new NHLEvents(2, iEventDao);
        assertEquals(nhlEvents2.getId(), 5);
        assertNull(nhlEvents2.getEndOfRegularSeason());
    }

    @Test
    public void getNextSeasonDateTest() throws Exception {
        NHLEvents nhlEvents = new NHLEvents(0, iEventDao);
        assertEquals(nhlEvents.getId(), 3);
        assertTrue(nhlEvents.getNextSeasonDate().equals(LocalDate.of(2021, Month.OCTOBER, 01)));

        NHLEvents nhlEvents2 = new NHLEvents(2, iEventDao);
        assertEquals(nhlEvents2.getId(), 5);
        assertTrue(nhlEvents2.getNextSeasonDate().equals(LocalDate.of(2021, Month.OCTOBER, 01)));

    }

    @Test
    public void setNextSeasonDateTest() {
        NHLEvents nhlEvents = new NHLEvents();
        LocalDate nextSeasonDate = LocalDate.of(2021, Month.OCTOBER, 01);
        nhlEvents.setNextSeasonDate(nextSeasonDate);
        assertTrue(nhlEvents.getNextSeasonDate().equals(LocalDate.of(2021, Month.OCTOBER, 01)));
    }

    @Test
    public void getEndOfRegularSeasonTest() throws Exception {
        NHLEvents nhlEvents2 = new NHLEvents(2, iEventDao);
        assertEquals(nhlEvents2.getId(), 5);
        assertEquals(null, nhlEvents2.getEndOfRegularSeason());
    }

    @Test
    public void setEndOfRegularSeasonTest() {
        NHLEvents nhlEvents = new NHLEvents();
        LocalDate endOfRegularSeason = LocalDate.of(2021, Month.APRIL, 01);
        nhlEvents.setNextSeasonDate(endOfRegularSeason);
        assertTrue(nhlEvents.getNextSeasonDate().equals(LocalDate.of(2021, Month.APRIL, 01)));
    }

    @Test
    public void getPlayerDraftDateTest() throws Exception {
        NHLEvents nhlEvents = new NHLEvents();
        iEventDao.loadEventById(3, nhlEvents);
        assertEquals(nhlEvents.getId(), 3);
        assertEquals(LocalDate.of(2021, Month.JULY, 15), nhlEvents.getPlayerDraftDate());
    }

    @Test
    public void setPlayerDraftDateTest() {
        NHLEvents nhlEvents = new NHLEvents();
        LocalDate playerDraftDate = LocalDate.of(2021, Month.JULY, 15);
        nhlEvents.setPlayerDraftDate(playerDraftDate);
        assertTrue(nhlEvents.getPlayerDraftDate().equals(LocalDate.of(2021, Month.JULY, 15)));
    }

    @Test
    public void getPlayOffStartDateTest() throws Exception {
        NHLEvents nhlEvents2 = new NHLEvents(1, iEventDao);
        assertEquals(nhlEvents2.getId(), 4);
        assertTrue(nhlEvents2.getPlayOffStartDate().equals(LocalDate.of(2021, Month.APRIL, 05)));
    }

    @Test
    public void getTradeDeadlineDateTest() throws Exception {
        NHLEvents nhlEvents2 = new NHLEvents(0, iEventDao);
        assertTrue(nhlEvents2.getTradeDeadlineDate().equals(LocalDate.of(2021, Month.FEBRUARY, 03)));
    }

    @Test
    public void checkTradeDeadlinePassedTest() throws Exception {
        NHLEvents nhlEvents2 = new NHLEvents(0, iEventDao);
        assertTrue(nhlEvents2.checkTradeDeadlinePassed(LocalDate.of(2022, Month.FEBRUARY, 03)));
        assertFalse(nhlEvents2.checkTradeDeadlinePassed(LocalDate.of(2020, Month.FEBRUARY, 03)));
    }

    @Test
    public void checkEndOfRegularSeasonTest() throws Exception {
        NHLEvents nhlEvents2 = new NHLEvents(0, iEventDao);
        assertTrue(nhlEvents2.checkEndOfRegularSeason(LocalDate.of(2021, Month.APRIL, 06)));
        assertFalse(nhlEvents2.checkEndOfRegularSeason(LocalDate.of(2020, Month.APRIL, 06)));
    }

    @Test
    public void checkRegularSeasonPassedTest() throws Exception {
        NHLEvents nhlEvents2 = new NHLEvents(0, iEventDao);
        assertTrue(nhlEvents2.checkRegularSeasonPassed(LocalDate.of(2021, Month.MAY, 07)));
        assertFalse(nhlEvents2.checkRegularSeasonPassed(LocalDate.of(2021, Month.APRIL, 06)));
    }

}
