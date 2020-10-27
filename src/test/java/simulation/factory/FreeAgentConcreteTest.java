package simulation.factory;

import db.dao.FreeAgentDao;
import org.junit.Before;
import org.junit.Test;
import simulation.model.FreeAgent;

import static org.junit.Assert.assertTrue;

public class FreeAgentConcreteTest {

    private FreeAgentConcrete freeAgentConcrete;

    @Before
    public void init() {
        freeAgentConcrete = new FreeAgentConcrete();
    }

    @Test
    public void newFreeAgentTest() {
        assertTrue(freeAgentConcrete.newFreeAgent() instanceof FreeAgent);
    }

    @Test
    public void newLoadFreeAgentFactory() {
        assertTrue(freeAgentConcrete.newLoadFreeAgentFactory() instanceof FreeAgentDao);
    }

    @Test
    public void newAddFreeAgentFactory() {
        assertTrue(freeAgentConcrete.newAddFreeAgentFactory() instanceof FreeAgentDao);
    }

}
