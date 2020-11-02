package validator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationTest {

    @Test
    public void isListNotEmptyTest() {
        Validation validation = new Validation();
        assertFalse(validation.isListNotEmpty(null));
        List<String> list = new ArrayList<>(Arrays.asList("a", "b"));
        assertTrue(validation.isListNotEmpty(list));
    }

    @Test
    public void isNotNullTest() {
        String a = null;
        Validation validation = new Validation();
        assertFalse(validation.isNotNull(a));
        a = "a";
        assertTrue(validation.isNotNull(a));
    }

    @Test
    public void isNotEmptyTest(){
        String a = null;
        Validation validation = new Validation();
        assertFalse(validation.isNotNull(a));
        a = "a";
        assertTrue(validation.isNotNull(a));
    }

}
