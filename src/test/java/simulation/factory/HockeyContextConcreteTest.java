package simulation.factory;

import org.junit.Test;
import simulation.state.IHockeyContext;

import static org.junit.Assert.assertTrue;

public class HockeyContextConcreteTest {

    @Test
    public void newHockeyContextTest() {
        HockeyContextConcrete hockeyContextConcrete = new HockeyContextConcrete();
        assertTrue(hockeyContextConcrete.newHockeyContext() instanceof IHockeyContext);
    }

}
