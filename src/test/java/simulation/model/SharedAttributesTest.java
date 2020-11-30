package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IDaoFactory;
import persistance.dao.IPlayerDao;
import simulation.dao.DaoFactoryMock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SharedAttributesTest {

    private static IDaoFactory daoFactory;
    private static IPlayerDao playerDao;
    private static IModelFactory modelFactory;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        playerDao = daoFactory.createPlayerDao();
        modelFactory = ModelFactory.getInstance();
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
        SharedAttributes sharedAttributes = new Player(1, playerDao);
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
