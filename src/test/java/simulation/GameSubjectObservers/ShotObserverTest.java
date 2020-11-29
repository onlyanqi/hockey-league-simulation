package simulation.GameSubjectObservers;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.UserMock;
import simulation.model.User;
import simulation.state.IHockeyContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ShotObserverTest {

    private static IHockeyContext hockeyContext;
    private static IUserDao userFactory;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
        hockeyContext.getUser().getLeague().initializeTeamStats();
    }

    @Test
    public void update() {
        IGameObserver shotObserver = new ShotObserver();
        shotObserver.update(hockeyContext.getUser().getLeague(), "Team11", 3);
        assertTrue(hockeyContext.getUser().getLeague().getTeamStatByTeamName("Team11").getShots() == 3);
        assertNotNull(hockeyContext.getUser().getLeague().getTeamStatByTeamName("Team11").getShots() == 3);
    }
}