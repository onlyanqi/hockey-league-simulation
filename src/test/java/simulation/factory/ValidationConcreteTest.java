package simulation.factory;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ValidationConcreteTest {

    @Test
    public void newValidationTest() {
        ValidationConcrete validationConcrete = new ValidationConcrete();
        assertNotNull(validationConcrete.newValidation());
    }

}
