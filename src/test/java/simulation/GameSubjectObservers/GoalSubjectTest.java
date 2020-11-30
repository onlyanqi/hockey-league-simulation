package simulation.GameSubjectObservers;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IGameDao;
import persistance.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.GameMock;
import simulation.mock.UserMock;
import simulation.model.User;
import simulation.state.IHockeyContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GoalSubjectTest {

    private static IHockeyContext hockeyContext;
    private static IUserDao userFactory;
    private static IGameDao gameFactory;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        gameFactory = new GameMock();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
        hockeyContext.getUser().getLeague().initializeTeamStats();
    }

    @Test
    public void testAttach() {
        GoalObserver goalObserver = new GoalObserver();
        GoalSubject goalSubject = new GoalSubject();
        goalSubject.attach(goalObserver);
        assertTrue(goalSubject.getListeners().size() == 1);
    }

    @Test
    public void testDetach() {
        GoalObserver goalObserver = new GoalObserver();
        GoalSubject goalSubject = new GoalSubject();
        goalSubject.attach(goalObserver);
        goalSubject.detach(goalObserver);
        assertTrue(goalSubject.getListeners().size() == 0);
    }

    @Test
    public void testNotifyObservers() {
        GoalObserver goalObserver = new GoalObserver();
        GoalSubject goalSubject = new GoalSubject();
        goalSubject.attach(goalObserver);
        goalSubject.notifyObservers(hockeyContext.getUser().getLeague(), "Team11", 2);
        assertTrue(hockeyContext.getUser().getLeague().getTeamStatByTeamName("Team11").getGoals() == 2);
        assertNotNull(hockeyContext.getUser().getLeague().getTeamStatByTeamName("Team11").getGoals() == 2);
    }
}