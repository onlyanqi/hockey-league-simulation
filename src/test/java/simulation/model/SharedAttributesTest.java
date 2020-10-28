package simulation.model;

import db.data.ISharedAttributesFactory;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class SharedAttributesTest {

    private static ISharedAttributesFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new SharedAttributesMock();
    }

    @Test
    public void defaultConstructorTest() {
        SharedAttributes sharedAttributes = new SharedAttributes();
        assertEquals(sharedAttributes.getId(), 0);
    }

    @Test
    public void parentObjTest() {
        SharedAttributes sharedAttributes = new SharedAttributes(1);
        assertEquals(sharedAttributes.getId(), 1);
    }

    @Test
    public void parentObjFactoryTest() throws Exception {
        SharedAttributes sharedAttributes = new SharedAttributes(1, factory);
        assertEquals(sharedAttributes.getId(), 1);
        assertEquals(sharedAttributes.getName(), "Parent");

        sharedAttributes = new SharedAttributes(2, factory);
        assertNull(sharedAttributes.getName());
    }

    @Test
    public void getIdTest(){
        SharedAttributes sharedAttributes = new SharedAttributes(1);
        assertEquals(sharedAttributes.getId(), 1);
    }

    @Test
    public void setIdTest(){
        SharedAttributes sharedAttributes = new SharedAttributes();
        int id = 1;
        sharedAttributes.setId(id);
        assertEquals(sharedAttributes.getId(), id);
    }

    @Test
    public void getNameTest() throws Exception {
        SharedAttributes sharedAttributes = new SharedAttributes(1, factory);
        assertTrue(sharedAttributes.getName().equals("Parent"));
    }

    @Test
    public void setNameTest(){
        SharedAttributes sharedAttributes = new SharedAttributes();
        String name = "name";
        sharedAttributes.setName(name);
        assertTrue(sharedAttributes.getName().equals(name));
    }

    @Test
    public void isNullTest(){
        SharedAttributes sharedAttributes = new SharedAttributes();
        assertTrue(sharedAttributes.isNull(null));
    }

    @Test
    public void isNotNullTest(){
        SharedAttributes sharedAttributes = new SharedAttributes();
        assertTrue(sharedAttributes.isNotNull(""));
    }

    @Test
    public void isNotEmptyTest(){
        SharedAttributes sharedAttributes = new SharedAttributes();
        assertTrue(sharedAttributes.isNotEmpty("name"));
    }

    @Test
    public void validNameTest(){
        SharedAttributes sharedAttributes = new SharedAttributes();
        assertTrue(sharedAttributes.isNotEmpty("name"));
    }

}
