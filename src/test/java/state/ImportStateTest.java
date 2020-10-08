package state;

import model.HockeyContext;
import model.User;
import org.icehockey.JSONControllerMock;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ImportStateTest {

    static HockeyContext hockeyContext;
    static IHockeyState hockeyState;
    static IHockeyState hockeyState2;
    static User user ;

    @BeforeClass
    public static void setState(){

        user = new User();
        user.setId(2);
        hockeyContext = new HockeyContext(user);
        hockeyState = new ImportState(hockeyContext, JSONControllerMock.getJSON(1));

        hockeyState2 = new ImportState(hockeyContext, JSONControllerMock.getJSON(2));

    }

    @Test
    public void entryTest() {

        assertTrue(true);
    }

    @Test
    public void processTest() throws Exception {
        hockeyState.process();
        assertEquals(hockeyContext.getLeague().getName(),"Dalhousie Hockey League");
        assertTrue(hockeyContext.getLeague().getConferenceList() != null);

        hockeyState2.process();
        assertEquals(hockeyContext.getLeague().getName(),"National Hockey League");
        assertTrue(hockeyContext.getLeague().getConferenceList() != null);


        assertEquals(hockeyContext.getLeague().getConferenceList().get(0).getName() , "Eastern Conference");
    }

    @Test
    public void exitTest(){
        assertEquals(hockeyState.exit(),null);
    }

}