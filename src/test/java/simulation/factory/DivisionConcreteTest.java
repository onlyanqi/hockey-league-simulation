package simulation.factory;

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

}
