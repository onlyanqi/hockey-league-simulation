package state;

import data.ILoadLeagueFactory;
import model.HockeyContext;
import model.LoadLeagueMock;
import model.User;
import org.icehockey.JSONControllerMock;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.BeforeClass;
import scala.reflect.internal.Trees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ImportStateTest {

    private static ILoadLeagueFactory factory;
    static User user ;
    IHockeyState state ;

    @BeforeClass
    public static void setAll(){

        factory = new LoadLeagueMock();
        user = new User();
        user.setName("user1");
        user.setId(1);



    }


    @Test
    public void processTest() throws Exception {
        HockeyContext hockeyContext = new HockeyContext();
        hockeyContext.setUser(user);

        JSONObject leagueObject = JSONControllerMock.getJSON(1);

        state = new ImportState(hockeyContext,leagueObject);



        //state.process();

//        assertEquals(hockeyContext.getUser().getLeague() , null);
//        assertEquals(hockeyContext.getUser().getLeague().getName() , "Dalhousie Hockey League");

    }

    @Test
    public void exitTest(){
        //assertEquals(hockeyState.exit(),null);
    }

}