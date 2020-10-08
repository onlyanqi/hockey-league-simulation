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
    static User user ;

    @BeforeClass
    public static void setState(){

//        user = new User();
//        UserMock mock = new UserMock();
//        mock.loadUserByName(1,user);
//
//        hockeyContext = new HockeyContext(user);
//        hockeyState = new ImportState(hockeyContext, JSONControllerMock.getJSON());
    }

    @Test
    public void entryTest() {

        assertTrue(true);
    }

    @Test
    public void processTest() {
//        hockeyState.process();
//        assertEquals(hockeyContext.getLeague().getName(),"Eastern Conference");
//        assertTrue(hockeyContext.getLeague().getConferenceList() != null);
    }

    @Test
    public void exitTest(){
//        assertEquals(hockeyState.exit(),null);
    }
}