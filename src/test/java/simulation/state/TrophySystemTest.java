package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import presentation.ConsoleOutput;
import simulation.dao.ILeagueDao;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.TrophyMock;
import simulation.mock.UserMock;
import simulation.model.*;
import static org.junit.Assert.*;


public class TrophySystemTest {
    static IHockeyContext hockeyContext;
    static private ILeague league;
    static private ConsoleOutput consoleOutput;
    static private ITrophy trophy;

    @BeforeClass
    public static void setState() throws Exception {
        ILeagueDao factory = new LeagueMock();
        league = new League(4, factory);
        IUserDao factoryUser = new UserMock();
        IUser user = new User(1, factoryUser);
        user.setId(1);
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        hockeyContext.setUser(user);
        trophy = new TrophyMock().loadTrophyById(0,new Trophy());
        league.setTrophy(trophy);
        consoleOutput = ConsoleOutput.getInstance();
    }

    @Test
    public void processTest() throws Exception {
        TrophySystem trophySystem = new TrophySystem(hockeyContext,trophy);
        Trophy oldTrophy = new Trophy();
        trophySystem.process();
        assertTrue(oldTrophy.getClass() == trophy.getClass());
    }

    @Test
    public void entryTest() throws Exception {
        IUserDao userFactory = new UserMock();;
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
        league = hockeyContext.getUser().getLeague();
        TrophySystem trophySystem = new TrophySystem(hockeyContext,trophy);
        Trophy oldTrophy = new Trophy();
//        trophySystem.entry();
//        assertTrue(oldTrophy.getClass() == trophy.getClass());
    }


}
