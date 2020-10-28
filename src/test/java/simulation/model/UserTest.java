package simulation.model;

import db.data.ILeagueFactory;
import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserTest {

    private static IUserFactory loadUserFactory;

    @BeforeClass
    public static void setFactoryObj() {
        loadUserFactory = new UserMock();
    }

    @Test
    public void defaultConstructorTest() {
        User user = new User();
        assertEquals(user.getId(), 0);
    }

    @Test
    public void userTest() {
        User user = new User(1);
        assertEquals(user.getId(), 1);
    }

    @Test
    public void userFactoryTest() throws Exception {
        User user = new User(1, loadUserFactory);
        assertEquals(user.getId(), 1);
        assertEquals(user.getName(), "User1");

        user = new User(2, loadUserFactory);
        assertNull(user.getName());
    }

    @Test
    public void getPasswordTest() throws Exception {
        User user = new User(1, loadUserFactory);
        assertEquals(user.getPassword(), ("Password1"));
    }

    @Test
    public void setPasswordTest() {
        User user = new User();
        String password = "Password";
        user.setPassword(password);
        assertEquals(user.getPassword(), (password));
    }

    @Test
    public void getLeagueTest() throws Exception {
        User user = new User(1, loadUserFactory);
        League league = user.getLeagueList().get(0);
        assertEquals(league.getId(), (1));
    }

    @Test
    public void setLeagueTest() throws Exception {
        User user = new User(1, loadUserFactory);
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        assertEquals(user.getLeagueList().get(0).getId(), league.getId());
    }

    @Test
    public void getLeagueListTest() throws Exception {
        User user = new User(1, loadUserFactory);
        League league = user.getLeagueList().get(0);
        assertEquals(league.getId(), (1));
    }

    @Test
    public void setLeagueListTest() throws Exception {
        User user = new User(1, loadUserFactory);
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        assertEquals(user.getLeagueList().get(0).getId(), league.getId());
    }

    @Test
    public void addUserTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("User1");
        user.addUser(loadUserFactory);
        assertEquals(1, user.getId());
        assertEquals("User1", (user.getName()));
    }


}
