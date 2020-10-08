package model;

import data.ILoadUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private static ILoadUserFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new LoadUserMock();
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
        User user = new User(1, factory);
        assertEquals(user.getId(), 1);
        assertEquals(user.getName(), "User1");

        user = new User(2, factory);
        assertNull(user.getName());
    }

    @Test
    public void getPasswordTest() throws Exception{
        User user = new User(1, factory);
        assertTrue(user.getPassword().equals("Password1"));
    }

    @Test
    public void setPasswordTest(){
        User user = new User();
        String password = "Password";
        user.setPassword(password);
        assertTrue(user.getPassword().equals(password));
    }

}
