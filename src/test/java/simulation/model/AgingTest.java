package simulation.model;

import db.data.IAgingDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.AgingMock;

import static org.junit.Assert.*;

public class AgingTest {
    private static IAgingDao loadAgingFactory;

    @BeforeClass
    public static void setFactoryObj() {
        loadAgingFactory = new AgingMock();
    }

    @Test
    public void defaultConstructorTest() {
        Aging aging = new Aging();
        assertNotEquals(aging.getId(), 0);
    }

    @Test
    public void agingTest() {
        Aging aging = new Aging(1);
        assertEquals(aging.getId(), 1);
    }

    @Test
    public void agingFactoryTest() throws Exception {
        Aging aging = new Aging(1, loadAgingFactory);
        assertEquals(aging.getId(), 1);
        assertEquals(aging.getAverageRetirementAge(), 35);
        assertEquals(aging.getMaximumAge(), 50);
        assertTrue("Maximum age (" + aging.getMaximumAge() + ") should be greater than average retirement age (" + aging.getAverageRetirementAge() + ")", aging.getMaximumAge() > aging.getAverageRetirementAge());
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenAverageAboveMaximum() {
        Aging aging = new Aging();
        int averageRetirementAge = 59;
        int maximumRetirementAge = 20;
        aging.setAverageRetirementAge(averageRetirementAge);
        aging.setMaximumAge(maximumRetirementAge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenAverageAgeIsNegativeNumber() {
        Aging aging = new Aging();
        int averageRetirementAge = -3;
        aging.setAverageRetirementAge(averageRetirementAge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenMaximumAgeIsNegativeNumber() {
        Aging aging = new Aging();
        int maximumRetirementAge = -50;
        aging.setMaximumAge(maximumRetirementAge);
    }

    @Test
    public void getAverageRetirementAgeTest() throws Exception {
        Aging aging = new Aging(1, loadAgingFactory);
        assertEquals(aging.getAverageRetirementAge(), 35);
    }

    @Test
    public void setAverageRetirementAgeTest() {
        Aging aging = new Aging();
        int averageRetirementAge = 29;
        aging.setAverageRetirementAge(averageRetirementAge);
        assertEquals(aging.getAverageRetirementAge(), averageRetirementAge);
    }


    @Test
    public void getMaximumAgeTest() throws Exception {
        Aging aging = new Aging(1, loadAgingFactory);
        assertEquals(aging.getMaximumAge(), 50);
    }

    @Test
    public void setMaximumAgeTest() {
        Aging aging = new Aging();
        int maximumAge = 52;
        aging.setMaximumAge(maximumAge);
        assertEquals(aging.getMaximumAge(), maximumAge);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        Aging aging = new Aging(1, loadAgingFactory);
        assertEquals(aging.getLeagueId(), 1);
    }

    @Test
    public void setLeagueIdTest() {
        Aging aging = new Aging();
        aging.setLeagueId(2);
        assertEquals(aging.getLeagueId(), 2);
    }


}
