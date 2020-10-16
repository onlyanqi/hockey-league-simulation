package factory;

import dao.AddUserDao;
import dao.LoadUserDao;
import simulation.data.ILoadUserFactory;
import model.LoadUserMock;
import simulation.model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class UserConcreteTest {

    private UserConcrete userConcrete;

    @Before
    public void init(){
        userConcrete = new UserConcrete();
    }

    @Test
    public void newUserTest(){
        assertTrue(userConcrete.newUser() instanceof User);
    }

    @Test
    public void newUserByNameTest() throws Exception {
        String name = "name";
        ILoadUserFactory loadUserFactory = new LoadUserMock();
        User user = userConcrete.newUserByName(name, loadUserFactory);
        assertTrue(user instanceof User);
    }

    @Test
    public void newLoadUserFactoryTest(){
        assertTrue(userConcrete.newLoadUserFactory() instanceof LoadUserDao);
    }

    @Test
    public void newAddUserFactoryTest(){
        assertTrue(userConcrete.newAddUserFactory() instanceof AddUserDao);
    }

}
