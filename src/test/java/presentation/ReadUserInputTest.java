package presentation;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertTrue;

public class ReadUserInputTest {

    @Test
    public void getInstanceTest() {
        IReadUserInput readUserInput = ReadUserInput.getInstance();
        assertTrue(readUserInput instanceof IReadUserInput);
    }

    @Test
    public void getUserTradeResponseTest() {
        String userInput = "a";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        IReadUserInput readUserInput = ReadUserInput.getInstance();
        assertTrue(userInput.equalsIgnoreCase(readUserInput.getUserTradeResponse()));
    }

}
