package simulation.factory;

import org.junit.Test;
import simulation.state.IHockeyContext;

import static org.junit.Assert.assertTrue;

public class HockeyContextConcreteTest {

    @Test
    public void newHockeyContextTest() {
        IHockeyContextFactory hockeyContextConcrete = HockeyContextConcrete.getInstance();
        assertTrue(hockeyContextConcrete.newHockeyContext() instanceof IHockeyContext);
    }

}
