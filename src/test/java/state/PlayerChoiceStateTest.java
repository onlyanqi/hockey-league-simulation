package state;

import org.junit.Test;
import org.junit.BeforeClass;
import simulation.state.HockeyContext;
import simulation.state.IHockeyState;
import simulation.state.PlayerChoiceState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PlayerChoiceStateTest {

    static HockeyContext hockeyContext;
    static IHockeyState hockeyState;
    static IHockeyState hockeyState2;

    @BeforeClass
    public static void setState(){
        hockeyContext = new HockeyContext();
        hockeyState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        hockeyState2 = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","1");
    }

    @Test
    public void exitTest(){
        assertTrue(hockeyState!=null);
        assertTrue(hockeyState2!=null);
    }
}