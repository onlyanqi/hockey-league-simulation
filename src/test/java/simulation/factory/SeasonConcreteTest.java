package simulation.factory;

import db.dao.SeasonDao;
import simulation.model.Season;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SeasonConcreteTest {

    private SeasonConcrete seasonConcrete;

    @Before
    public void init(){
        seasonConcrete = new SeasonConcrete();
    }

    @Test
    public void newSeasonTest(){
        assertTrue(seasonConcrete.newSeason() instanceof Season);
    }

    @Test
    public void newLoadSeasonFactoryTest(){
        assertTrue(seasonConcrete.newLoadSeasonFactory() instanceof SeasonDao);
    }

    @Test
    public void newAddSeasonFactoryTest(){
        assertTrue(seasonConcrete.newAddSeasonFactory() instanceof SeasonDao);
    }

}
