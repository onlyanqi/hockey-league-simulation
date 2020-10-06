package state;

import model.HockeyContext;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.assertTrue;


public class ImportStateTest {

    static HockeyContext hockeyContext;
    static IHockeyState hockeyState;

    @BeforeClass
    public static void setState(){
        hockeyContext = new HockeyContext();
        hockeyState = new ImportState(hockeyContext);
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
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"Do you want to create a team or Load a team?","importState");
        assertTrue((hockeyState.exit()) instanceof  PlayerChoiceState);
    }
}