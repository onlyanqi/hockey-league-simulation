package simulation.state;

import db.data.ILeagueFactory;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.JSONControllerMock;
import simulation.model.LeagueMock;
import simulation.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ImportStateTest {

    static User user;
    private static ILeagueFactory factory;
    IHockeyState state;

    @BeforeClass
    public static void setAll() {

        factory = new LeagueMock();
        user = new User();
        user.setName("user1");
        user.setId(1);

    }


    @Test
    public void processTest() throws Exception {
        HockeyContext hockeyContext = new HockeyContext();
        hockeyContext.setUser(user);

        assertEquals(hockeyContext.getUser().getId(), user.getId());
        assertEquals(hockeyContext.getUser().getName(), user.getName());


        JSONObject leagueObject = JSONControllerMock.getJSON(1);
        state = new ImportState(hockeyContext, leagueObject);

        state.process();
        System.out.println(hockeyContext.getUser().getLeague().getName());

        assertTrue(hockeyContext.getUser().getLeague() != null);
        assertEquals(hockeyContext.getUser().getLeague().getName(), "Dalhousie Hockey League");

    }

    @Test
    public void exitTest() {
        //assertEquals(hockeyState.exit(),null);
    }

}