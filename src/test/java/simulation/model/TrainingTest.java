package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.ITrainingDao;
import simulation.mock.TrainingMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TrainingTest {
    private static ITrainingDao trainingFactory;

    @BeforeClass
    public static void setFactoryObject() {
        trainingFactory = new TrainingMock();
    }

    @Test
    public void getDaysUntilStatIncreaseCheckTest() throws Exception {
        Training training = new Training();
        trainingFactory.loadTrainingByLeagueId(1, training);
        assertEquals(50, training.getDaysUntilStatIncreaseCheck());
        assertNotEquals(20, training.getDaysUntilStatIncreaseCheck());
        assertNotEquals(null, training.getDaysUntilStatIncreaseCheck());
    }

    @Test
    public void setDaysUntilStatIncreaseCheckTest() throws Exception {
        Training training = new Training();
        trainingFactory.loadTrainingByLeagueId(1, training);
        training.setDaysUntilStatIncreaseCheck(30);
        assertEquals(30, training.getDaysUntilStatIncreaseCheck());
        assertNotEquals(20, training.getDaysUntilStatIncreaseCheck());
        assertNotEquals(null, training.getDaysUntilStatIncreaseCheck());
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        Training training = new Training();
        trainingFactory.loadTrainingByLeagueId(1, training);
        assertEquals(training.getLeagueId(), 1);
        assertNotEquals(training.getLeagueId(), 0);
        assertNotEquals(training.getLeagueId(), null);
    }

    @Test
    public void setLeagueIdTest() throws Exception {
        Training training = new Training();
        trainingFactory.loadTrainingByLeagueId(1, training);
        training.setLeagueId(5);
        assertEquals(training.getLeagueId(), 5);
        assertNotEquals(training.getLeagueId(), 0);
        assertNotEquals(training.getLeagueId(), null);
    }

    @Test
    public void getIdTest() throws Exception {
        Training training = new Training();
        trainingFactory.loadTrainingByLeagueId(1, training);
        assertEquals(training.getId(), 1);
        assertNotEquals(training.getId(), null);
        assertNotEquals(training.getId(), 0);
    }

    @Test
    public void setIdTest() throws Exception {
        Training training = new Training();
        trainingFactory.loadTrainingByLeagueId(1, training);
        training.setId(5);
        assertEquals(training.getId(), 5);
        assertNotEquals(training.getId(), null);
        assertNotEquals(training.getId(), 0);
    }


}
