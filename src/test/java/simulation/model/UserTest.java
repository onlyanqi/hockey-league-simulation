package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IDaoFactory;
import persistance.dao.ILeagueDao;
import persistance.dao.IUserDao;
import simulation.dao.DaoFactoryMock;
import static org.junit.Assert.*;

public class UserTest {

    private static IDaoFactory daoFactory;
    private static IUserDao userDao;
    private static IModelFactory modelFactory;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        userDao = daoFactory.createUserDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void defaultConstructorTest() {
        IUser user = modelFactory.createUser();
        assertNotEquals(user.getId(), 0);
    }

    @Test
    public void userTest() {
        IUser user = modelFactory.createUserWithId(1);
        assertEquals(user.getId(), 1);
    }

    @Test
    public void userFactoryTest() throws Exception {
        IUser user = modelFactory.createUserWithIdDao(1, userDao);
        assertEquals(user.getId(), 1);
        assertEquals(user.getName(), "User1");

        user = modelFactory.createUserWithIdDao(2, userDao);
        assertNull(user.getName());
    }

    @Test
    public void getPasswordTest() throws Exception {
        IUser user = modelFactory.createUserWithIdDao(1, userDao);
        assertEquals(user.getPassword(), ("Password1"));
    }

    @Test
    public void setPasswordTest() {
        IUser user = modelFactory.createUser();
        String password = "Password";
        user.setPassword(password);
        assertEquals(user.getPassword(), (password));
    }

    @Test
    public void getLeagueTest() throws Exception {
        IUser user = modelFactory.createUserWithIdDao(1, userDao);
        ILeague league = user.getLeagueList().get(0);
        assertEquals(league.getId(), (1));
    }

    @Test
    public void setLeagueTest() throws Exception {
        IUser user = modelFactory.createUserWithIdDao(1, userDao);
        ILeagueDao leagueFactory = daoFactory.createLeagueDao();
        ILeague league = modelFactory.createLeagueWithIdDao(1, leagueFactory);
        assertEquals(user.getLeagueList().get(0).getId(), league.getId());
    }

    @Test
    public void getLeagueListTest() throws Exception {
        IUser user = modelFactory.createUserWithIdDao(1, userDao);
        ILeague league = user.getLeagueList().get(0);
        assertEquals(league.getId(), (1));
    }

    @Test
    public void setLeagueListTest() throws Exception {
        IUser user = modelFactory.createUserWithIdDao(1, userDao);
        ILeagueDao leagueFactory = daoFactory.createLeagueDao();
        ILeague league = modelFactory.createLeagueWithIdDao(1, leagueFactory);
        assertEquals(user.getLeagueList().get(0).getId(), league.getId());
    }

    @Test
    public void addUserTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("User1");
        user.addUser(userDao);
        assertEquals(1, user.getId());
        assertEquals("User1", (user.getName()));
    }


}
