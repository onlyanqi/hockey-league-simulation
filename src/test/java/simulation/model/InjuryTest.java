package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IInjuryDao;
import simulation.mock.InjuryMock;

import static org.junit.Assert.*;

public class InjuryTest {
    private static IInjuryDao loadInjuryFactory;

    @BeforeClass
    public static void setFactoryObj() {
        loadInjuryFactory = new InjuryMock();
    }

    @Test
    public void defaultConstructorTest() {
        Injury injury = new Injury();
        assertNotEquals(injury.getId(), 0);
    }

    @Test
    public void injuryTest() {
        Injury injury = new Injury(1);
        assertEquals(injury.getId(), 1);
    }

    @Test
    public void injuryFactoryTest() throws Exception {
        Injury injury = new Injury(1, loadInjuryFactory);
        assertEquals(injury.getId(), 1);
        assertEquals(injury.getInjuryDaysLow(), 20);
        assertEquals(injury.getInjuryDaysHigh(), 200);
        assertTrue("InjuryDaysHigh (" + injury.getInjuryDaysHigh() + ") should be greater than InjuryDaysLow (" + injury.getInjuryDaysLow() + ")", injury.getInjuryDaysHigh() > injury.getInjuryDaysLow());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenRandomInjuryChanceMoreThanOne() throws Exception {
        Injury injury = new Injury(2, loadInjuryFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenInjuryDaysLowBelowZero() throws Exception {
        Injury injury = new Injury(3, loadInjuryFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenInjuryDaysHighBelowZero() throws Exception {
        Injury injury = new Injury(4, loadInjuryFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenInjuryDaysHighLowerThanInjuryDaysLow() throws Exception {
        Injury injury = new Injury(5, loadInjuryFactory);
    }

    @Test
    public void getRandomInjuryChanceTest() throws Exception {
        Injury injury = new Injury(6, loadInjuryFactory);
        assertEquals(injury.getRandomInjuryChance(), (Double) 0.05);
    }

    @Test
    public void setRandomInjuryChanceTest() {
        Injury injury = new Injury();
        injury.setRandomInjuryChance(0.06);
        assertEquals(injury.getRandomInjuryChance(), (Double) 0.06);
    }

    @Test
    public void getInjuryDaysLowTest() throws Exception {
        Injury injury = new Injury(1, loadInjuryFactory);
        assertEquals(injury.getInjuryDaysLow(), 20);
    }

    @Test
    public void setInjuryDaysLowTest() {
        Injury injury = new Injury();
        injury.setInjuryDaysLow(30);
        assertEquals(injury.getInjuryDaysLow(), 30);
    }

    @Test
    public void getInjuryDaysHighTest() throws Exception {
        Injury injury = new Injury(1, loadInjuryFactory);
        assertEquals(injury.getInjuryDaysHigh(), 200);
    }

    @Test
    public void setInjuryDaysHighTest() {
        Injury injury = new Injury();
        injury.setInjuryDaysHigh(60);
        assertEquals(injury.getInjuryDaysHigh(), 60);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        Injury injury = new Injury(1, loadInjuryFactory);
        assertEquals(injury.getLeagueId(), 1);
    }

    @Test
    public void setLeagueIdTest() {
        Injury injury = new Injury();
        injury.setLeagueId(3);
        assertEquals(injury.getLeagueId(), 3);
    }
}
