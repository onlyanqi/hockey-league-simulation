package simulation.state;

import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.UserMock;
import simulation.model.User;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class InternalStateTest {
    private static IUserFactory userFactory;
    private static HockeyContext hockeyContext;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContext = new HockeyContext();
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
