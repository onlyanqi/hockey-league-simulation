package factory;

import state.HockeyContext;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class HockeyContextConcreteTest {

    @Test
    public void newHockeyContextTest(){
        HockeyContextConcrete hockeyContextConcrete = new HockeyContextConcrete();
        assertTrue(hockeyContextConcrete.newHockeyContext() instanceof HockeyContext);
    }

}
