package simulation.model;

import simulation.dao.IPlayerDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.PlayerMock;

import static org.junit.Assert.*;

public class SharedAttributesTest {

    private static IPlayerDao playerFactory;

    @BeforeClass
    public static void setFactoryObj() {
        playerFactory = new PlayerMock();
    }

    @Test
    public void getIdTest() {
        SharedAttributes sharedAttributes = new Player(1);
        assertEquals(sharedAttributes.getId(), 1);
    }

    @Test
    public void setIdTest() {
        SharedAttributes sharedAttributes = new Conference();
        int id = 1;
        sharedAttributes.setId(id);
        assertEquals(sharedAttributes.getId(), id);
    }

    @Test
    public void getNameTest() throws Exception {
        SharedAttributes sharedAttributes = new Player(1, playerFactory);
        assertTrue(sharedAttributes.getName().equals("Player1"));
    }

    @Test
    public void setNameTest() {
        SharedAttributes sharedAttributes = new Player();
        String name = "name";
        sharedAttributes.setName(name);
        assertTrue(sharedAttributes.getName().equals(name));
    }

    @Test
    public void validNameTest() {
        SharedAttributes sharedAttributes = new Player();
        sharedAttributes.setName("name");
        assertTrue(sharedAttributes.validName());
    }

}
