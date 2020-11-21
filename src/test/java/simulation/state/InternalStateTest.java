package simulation.state;

import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.UserMock;
import simulation.model.User;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class InternalStateTest {
    private static IUserFactory userFactory;
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
    }


    @Test
    public void initConstructorTest() {
        assertNotNull(new InternalState(hockeyContext));
    }

    @Test
    public void exitTest() {
        InternalState state = new InternalState(hockeyContext);
        assertNull(state.exit());
    }
}
