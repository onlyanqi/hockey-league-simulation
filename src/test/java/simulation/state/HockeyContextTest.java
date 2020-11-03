package simulation.state;


import db.data.ILeagueFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.model.User;

import static org.junit.Assert.assertEquals;

public class HockeyContextTest {

    static User user;
    private static ILeagueFactory factory;

    @BeforeClass
    public static void setAll() {
        factory = new LeagueMock();
        user = new User();
        user.setName("user1");
        user.setId(1);
    }

    @Test
    public void checkContextTest() throws Exception {
        HockeyContext hockeyContext = new HockeyContext();
        hockeyContext.setUser(user);

        assertEquals(hockeyContext.getUser().getId(), user.getId());
        assertEquals(hockeyContext.getUser().getName(), user.getName());
    }

}