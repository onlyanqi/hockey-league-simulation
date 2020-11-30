package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.ICoachDao;
import simulation.mock.CoachMock;

import static org.junit.Assert.*;

public class CoachTest {
    private static ICoachDao loadCoachFactory;

    @BeforeClass
    public static void setFactoryObj() {
        loadCoachFactory = new CoachMock();
    }

    @Test
    public void defaultConstructorTest() {
        Coach coach = new Coach();
        assertNotEquals(coach.getId(), 0);
    }

    @Test
    public void coachTest() {
        Coach coach = new Coach(1);
        assertEquals(coach.getId(), 1);
    }

    @Test
    public void coachObjectTest() {
        Coach coach = new Coach((Coach) null);
        assertEquals(coach.getId(), 0);
    }

    @Test
    public void coachFactoryTest() throws Exception {
        Coach coach = new Coach(1, loadCoachFactory);
        assertEquals(coach.getId(), 1);
    }

    @Test
    public void coachFactoryNullTest() throws Exception {
        Coach coach = new Coach(1, null);
        assertEquals(coach.getId(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenSavingNotInRange() throws Exception {
        Coach coach = new Coach(5, loadCoachFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenCheckingNotInRange() throws Exception {
        Coach coach = new Coach(6, loadCoachFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenSkatingNotInRange() throws Exception {
        Coach coach = new Coach(7, loadCoachFactory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenShootingNotInRange() throws Exception {
        Coach coach = new Coach(8, loadCoachFactory);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        Coach coach = new Coach(1, loadCoachFactory);
        assertEquals(coach.getLeagueId(), 1);
    }

    @Test
    public void setLeagueIdTest() {
        Coach coach = new Coach();
        coach.setLeagueId(2);
        assertEquals(coach.getLeagueId(), 2);
    }

    @Test
    public void getTeamIdTest() throws Exception {
        Coach coach = new Coach(1, loadCoachFactory);
        assertEquals(coach.getLeagueId(), 1);
    }

    @Test
    public void setTeamIdTest() {
        Coach coach = new Coach();
        coach.setTeamId(3);
        assertEquals(coach.getTeamId(), 3);
    }

    @Test
    public void getNameTest() throws Exception {
        Coach coach = new Coach(2, loadCoachFactory);
        assertEquals(coach.getName(), "Sam Smith");
    }

    @Test
    public void setNameTest() {
        Coach coach = new Coach();
        coach.setName("Wang");
        assertEquals(coach.getName(), "Wang");
    }

    @Test
    public void setSkatingTest() {
        Coach coach = new Coach();
        coach.setSkating(null);
        assertNull(coach.getSkating());
    }

    @Test
    public void setShootingTest() {
        Coach coach = new Coach();
        coach.setShooting(null);
        assertNull(coach.getShooting());
    }

    @Test
    public void setCheckingTest() {
        Coach coach = new Coach();
        coach.setChecking(null);
        assertNull(coach.getChecking());
    }

    @Test
    public void setSavingTest() {
        Coach coach = new Coach();
        coach.setSaving(null);
        assertNull(coach.getSaving());
    }
}
