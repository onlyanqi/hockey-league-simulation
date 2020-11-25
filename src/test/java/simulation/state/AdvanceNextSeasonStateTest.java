package simulation.state;

import simulation.dao.ILeagueDao;
import simulation.dao.IPlayerDao;
import simulation.dao.IUserDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.PlayerMock;
import simulation.mock.UserMock;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
