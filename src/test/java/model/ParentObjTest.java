package model;

import data.IParentObjFactory;
import org.junit.Test;
import org.junit.BeforeClass;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class ParentObjTest {

    private static IParentObjFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new ParentObjMock();
    }

    @Test
    public void defaultConstructorTest() {
        ParentObj parentObj = new ParentObj();
        assertEquals(parentObj.getId(), 0);
    }

    @Test
    public void parentObjTest() {
        ParentObj parentObj = new ParentObj(1);
        assertEquals(parentObj.getId(), 1);
    }

    @Test
    public void parentObjFactoryTest(){
        ParentObj parentObj = new ParentObj(1, factory);
        assertEquals(parentObj.getId(), 1);
        assertEquals(parentObj.getName(), "Parent");

        parentObj = new ParentObj(2, factory);
        assertNull(parentObj.getName());
    }

    @Test
    public void getIdTest(){
        ParentObj parentObj = new ParentObj(1);
        assertEquals(parentObj.getId(), 1);
    }

    @Test
    public void setIdTest(){
        ParentObj parentObj = new ParentObj();
        long id = 1;
        parentObj.setId(id);
        assertEquals(parentObj.getId(), id);
    }

    @Test
    public void getNameTest(){
        ParentObj parentObj = new ParentObj(1, factory);
        assertTrue(parentObj.getName().equals("Parent"));
    }

    @Test
    public void setNameTest(){
        ParentObj parentObj = new ParentObj();
        String name = "name";
        parentObj.setName(name);
        assertTrue(parentObj.getName().equals(name));
    }

    @Test
    public void getStartDateTest(){
        ParentObj parentObj = new ParentObj(1, factory);
        assertTrue(parentObj.getStartDate().equals(new Date(2000, 00, 00)));
    }

    @Test
    public void setStartDateTest(){
        ParentObj parentObj = new ParentObj();
        Date startDate = new Date(1992, 04, 11);
        parentObj.setStartDate(startDate);
        assertTrue(parentObj.getStartDate().equals(startDate));
    }

    @Test
    public void getEndDateTest(){
        ParentObj parentObj = new ParentObj(1, factory);
        assertTrue(parentObj.getEndDate().equals(new Date(2050, 0, 0)));
    }

    @Test
    public void setEndDateTest(){
        ParentObj parentObj = new ParentObj();
        Date endDate = new Date(2050, 0, 0);
        parentObj.setEndDate(endDate);
        assertTrue(parentObj.getEndDate().equals(endDate));
    }

    @Test
    public void isNullTest(){
        ParentObj parentObj = new ParentObj();
        assertTrue(parentObj.isNull(null));
    }

    @Test
    public void isNotNullTest(){
        ParentObj parentObj = new ParentObj();
        assertTrue(parentObj.isNotNull(""));
    }

    @Test
    public void isNotEmptyTest(){
        ParentObj parentObj = new ParentObj();
        assertTrue(parentObj.isNotEmpty("name"));
    }

    @Test
    public void validNameTest(){
        ParentObj parentObj = new ParentObj();
        assertTrue(parentObj.isNotEmpty("name"));
    }

    @Test
    public void validEndDateTest(){
        ParentObj parentObj = new ParentObj(1, factory);
        int value = parentObj.getEndDate().compareTo(parentObj.getStartDate());
        assertTrue(value >= 0);
    }


}
