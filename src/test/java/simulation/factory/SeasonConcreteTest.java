package simulation.factory;

import org.junit.Before;
import org.junit.Test;
import simulation.model.Season;

import static org.junit.Assert.assertTrue;

public class SeasonConcreteTest {

    private SeasonConcrete seasonConcrete;

    @Before
    public void init() {
        seasonConcrete = new SeasonConcrete();
    }

    @Test
    public void newSeasonTest() {
        assertTrue(seasonConcrete.newSeason() instanceof Season);
    }


}
