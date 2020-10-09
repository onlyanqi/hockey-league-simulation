package model;

import data.IAddUserFactory;
import data.ILoadLeagueFactory;
import data.ILoadUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private static ILoadUserFactory loadUserFactory;
    private static IAddUserFactory addUserFactory;

    @BeforeClass
    public static void setFactoryObj(){
        loadUserFactory = new LoadUserMock();
        addUserFactory = new AddUserMock();
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
    public void userFactoryTest() throws Exception{
        User user = new User(1, loadUserFactory);
        assertEquals(user.getId(), 1);
        assertEquals(user.getName(), "User1");

        user = new User(2, loadUserFactory);
        assertNull(user.getName());
    }

    @Test
    public void getPasswordTest() throws Exception{
        User user = new User(1, loadUserFactory);
        assertTrue(user.getPassword().equals("Password1"));
    }

    @Test
    public void setPasswordTest(){
        User user = new User();
        String password = "Password";
        user.setPassword(password);
        assertTrue(user.getPassword().equals(password));
    }

    @Test
    public void getLeagueTest() throws Exception{
        User user = new User(1, loadUserFactory);
        League league = user.getLeagueList().get(0);
        assertTrue(league.getId() == (1));
    }

    @Test
    public void setLeagueTest() throws Exception {
        User user = new User(1, loadUserFactory);
        ILoadLeagueFactory leagueFactory = new LoadLeagueMock();
        League league = new League(1, leagueFactory);
        assertTrue(user.getLeagueList().get(0).getId() == league.getId());
    }

    @Test
    public void getLeagueListTest() throws Exception{
        User user = new User(1, loadUserFactory);
        League league = user.getLeagueList().get(0);
        assertTrue(league.getId() == (1));
    }

    @Test
    public void setLeagueListTest() throws Exception {
        User user = new User(1, loadUserFactory);
        ILoadLeagueFactory leagueFactory = new LoadLeagueMock();
        League league = new League(1, leagueFactory);
        assertTrue(user.getLeagueList().get(0).getId() == league.getId());
    }

    @Test
    public void addUserTest() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("User1");
        user.addUser(addUserFactory);
        assertTrue(1 == user.getId());
        assertTrue("User1".equals(user.getName()));
    }



}
