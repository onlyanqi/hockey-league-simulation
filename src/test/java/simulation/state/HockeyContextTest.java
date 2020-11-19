package simulation.state;


import db.data.ILeagueFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.model.User;

import static org.junit.Assert.assertEquals;

public class HockeyContextTest {

    private static User user;
    private static ILeagueFactory factory;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void setAll() {
        factory = new LeagueMock();
        user = new User();
        user.setName("user1");
        user.setId(1);
        hockeyContextFactory = new HockeyContextConcrete();
    }

    @Test
    public void checkContextTest() throws Exception {
        IHockeyContext hockeyContext = hockeyContextFactory.newHockeyContext();
        hockeyContext.setUser(user);

        assertEquals(hockeyContext.getUser().getId(), user.getId());
        assertEquals(hockeyContext.getUser().getName(), user.getName());
    }

}