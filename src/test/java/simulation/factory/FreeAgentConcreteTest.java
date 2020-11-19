package simulation.factory;

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


}
