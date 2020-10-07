package state;

import model.HockeyContext;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.assertTrue;


public class PlayerChoiceStateTest {

    static HockeyContext hockeyContext;
    static IHockeyState hockeyState;

    @BeforeClass
    public static void setState(){
        hockeyContext = new HockeyContext();
        hockeyState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
    }

    @Test
    public void entryTest() {

        assertTrue(true);
    }

    @Test
    public void processTest() {
        assertTrue(true);
    }

    @Test
    public void exitTest(){
        assertTrue(true);
    }
}