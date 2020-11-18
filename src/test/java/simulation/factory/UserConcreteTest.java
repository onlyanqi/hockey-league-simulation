package simulation.factory;

import db.data.IUserFactory;
import org.junit.Before;
import org.junit.Test;
import simulation.mock.UserMock;
import simulation.model.User;

import static org.junit.Assert.assertTrue;

public class UserConcreteTest {

    private UserConcrete userConcrete;

    @Before
    public void init() {
        userConcrete = new UserConcrete();
    }

    @Test
    public void newUserTest() {
        assertTrue(userConcrete.newUser() instanceof User);
    }

    @Test
    public void newUserByNameTest() throws Exception {
        String name = "name";
        IUserFactory loadUserFactory = new UserMock();
        User user = userConcrete.newUserByName(name, loadUserFactory);
        assertTrue(user instanceof User);
    }

}
