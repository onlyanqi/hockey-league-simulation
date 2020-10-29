package simulation.model;

import db.data.IAgingFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.AgingMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AgingTest {
    private static IAgingFactory loadAgingFactory;

    @BeforeClass
    public static void setFactoryObj(){
        loadAgingFactory = new AgingMock();
    }

    @Test
    public void defaultConstructorTest() {
        Aging aging = new Aging();
        assertEquals(aging.getId(), 0);
    }

    @Test
    public void agingTest() {
        Aging aging = new Aging(1);
        assertEquals(aging.getId(), 1);
    }

    @Test
    public void agingFactoryTest() throws Exception {
        Aging aging = new Aging(1, loadAgingFactory);
        assertEquals(aging.getId(),1);
        assertEquals(aging.getAverageRetirementAge(),35);
        assertEquals(aging.getMaximumAge(),50);
        assertTrue("Maximum age (" + aging.getMaximumAge() + ") should be greater than current (" + aging.getAverageRetirementAge() + ")", aging.getMaximumAge() > aging.getAverageRetirementAge());

    }

    @Test
    public void getAverageRetirementAgeTest() throws Exception {
        Aging aging = new Aging(1, loadAgingFactory);
        assertEquals(aging.getAverageRetirementAge(), 35);
    }

    @Test
    public void setAverageRetirementAgeTest(){
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
    public void setMaximumAgeTest(){
        Aging aging = new Aging();
        int maximumAge = 52;
        aging.setMaximumAge(maximumAge);
        assertEquals(aging.getMaximumAge(), maximumAge);
    }

}
