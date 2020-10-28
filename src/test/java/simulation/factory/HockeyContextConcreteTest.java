package simulation.factory;

import factory.HockeyContextConcrete;
import org.junit.Test;
import simulation.state.HockeyContext;

import static org.junit.Assert.assertTrue;

public class HockeyContextConcreteTest {

    @Test
    public void newHockeyContextTest() {
        HockeyContextConcrete hockeyContextConcrete = new HockeyContextConcrete();
        assertTrue(hockeyContextConcrete.newHockeyContext() instanceof HockeyContext);
    }

}
