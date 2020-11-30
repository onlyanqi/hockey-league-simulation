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

public class SaveSubjectTest {

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
    public void testAttach() {
        SaveObserver saveObserver = new SaveObserver();
        SaveSubject saveSubject = new SaveSubject();
        saveSubject.attach(saveObserver);
        assertTrue(saveSubject.getListeners().size() == 1);
    }

    @Test
    public void testDetach() {
        SaveObserver saveObserver = new SaveObserver();
        SaveSubject saveSubject = new SaveSubject();
        saveSubject.attach(saveObserver);
        saveSubject.detach(saveObserver);
        assertTrue(saveSubject.getListeners().size() == 0);
    }

    @Test
    public void testNotifyObservers() {
        SaveObserver saveObserver = new SaveObserver();
        SaveSubject saveSubject = new SaveSubject();
        saveSubject.attach(saveObserver);
        saveSubject.notifyObservers(hockeyContext.getUser().getLeague(), "Team11", 2);
        assertTrue(hockeyContext.getUser().getLeague().getTeamStatByTeamName("Team11").getSaves() == 2);
        assertNotNull(hockeyContext.getUser().getLeague().getTeamStatByTeamName("Team11").getSaves() == 2);
    }
}