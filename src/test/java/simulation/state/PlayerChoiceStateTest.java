package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class PlayerChoiceStateTest {

    static IHockeyContext hockeyContext;
    static IHockeyState hockeyState;
    static IHockeyState hockeyState2;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void setState() {
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        hockeyState = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "createOrLoadTeam");
        hockeyState2 = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "1");
    }

    @Test
    public void exitTest() {
        assertFalse(hockeyState == null);
        assertFalse(hockeyState2 == null);
    }
}