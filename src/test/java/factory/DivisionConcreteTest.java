package factory;

import dao.AddDivisionDao;
import dao.LoadDivisionDao;
import model.Division;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DivisionConcreteTest {

    private DivisionConcrete divisionConcrete;

    @Before
    public void init(){
        divisionConcrete = new DivisionConcrete();
    }

    @Test
    public void newDivisionTest(){
        assertTrue(divisionConcrete.newDivision() instanceof Division);
    }

    @Test
    public void newLoadDivisionFactory(){
        assertTrue(divisionConcrete.newLoadDivisionFactory() instanceof LoadDivisionDao);
    }

    @Test
    public void newAddDivisionFactory(){
        assertTrue(divisionConcrete.newAddDivisionFactory() instanceof AddDivisionDao);
    }

}
