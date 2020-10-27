package simulation.factory;

import db.dao.DivisionDao;
import org.junit.Before;
import org.junit.Test;
import simulation.model.Division;

import static org.junit.Assert.assertTrue;

public class DivisionConcreteTest {

    private DivisionConcrete divisionConcrete;

    @Before
    public void init() {
        divisionConcrete = new DivisionConcrete();
    }

    @Test
    public void newDivisionTest() {
        assertTrue(divisionConcrete.newDivision() instanceof Division);
    }

    @Test
    public void newLoadDivisionFactory() {
        assertTrue(divisionConcrete.newLoadDivisionFactory() instanceof DivisionDao);
    }

    @Test
    public void newAddDivisionFactory() {
        assertTrue(divisionConcrete.newAddDivisionFactory() instanceof DivisionDao);
    }

}
