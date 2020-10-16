package state;


import simulation.data.ILoadLeagueFactory;
import model.LoadLeagueMock;
import simulation.model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.state.HockeyContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HockeyContextTest {

    private static ILoadLeagueFactory factory;
    static User user ;

    @BeforeClass
    public static void setAll(){
        factory = new LoadLeagueMock();
        user = new User();
        user.setName("user1");
        user.setId(1);
    }

    @Test
    public void checkContextTest() throws Exception {
        HockeyContext hockeyContext = new HockeyContext();
        hockeyContext.setUser(user);

        assertEquals(hockeyContext.getUser().getId(),user.getId());
        assertEquals(hockeyContext.getUser().getName(),user.getName());
    }

}