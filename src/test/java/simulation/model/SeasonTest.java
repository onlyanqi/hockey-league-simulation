package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.DaoFactoryMock;
import simulation.dao.IDaoFactory;
import simulation.dao.ISeasonDao;
import simulation.mock.SeasonMock;

import static org.junit.Assert.*;

public class SeasonTest {

    private static IDaoFactory daoFactory;
    private static ISeasonDao seasonDao;
    private static IModelFactory modelFactory;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        seasonDao = daoFactory.createSeasonDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void defaultConstructorTest() {
        ISeason season = modelFactory.createSeason();
        assertNotEquals(season.getId(), 0);
    }

    @Test
    public void seasonTest() {
        ISeason season = modelFactory.createSeasonWithId(1);
        assertEquals(season.getId(), 1);
    }

    @Test
    public void seasonFactoryTest() throws Exception {
        ISeason season = modelFactory.createSeasonWithIdDao(1, seasonDao);
        assertEquals(season.getId(), 1);
        assertEquals(season.getName(), "Season1");

        season = modelFactory.createSeasonWithIdDao(2, seasonDao);
        assertNull(season.getName());
    }

    @Test
    public void addSeasonTest() throws Exception {
        Season season = new Season();
        season.setId(1);
        season.setName("Season1");
        season.addSeason(seasonDao);
        assertTrue(1 == season.getId());
        assertTrue("Season1".equals(season.getName()));
    }

}
