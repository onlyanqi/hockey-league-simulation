package simulation.state;

import org.junit.BeforeClass;
import simulation.dao.IPlayerDao;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.PlayerMock;
import simulation.mock.UserMock;
import simulation.model.User;

public class AdvanceNextSeasonStateTest {

    private static IPlayerDao playerFactory;
    private static IUserDao userFactory;
    private static IHockeyContext hockeyContext;

    @BeforeClass
    public static void init() throws Exception {
        playerFactory = new PlayerMock();
        userFactory = new UserMock();
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
    }


}
