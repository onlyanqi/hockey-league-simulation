package util;

import common.Constants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PropertyFileReaderTest {

    @Test
    public void loadPropertyFileNoExceptionTest(){
        PropertyFileReader read = new PropertyFileReader();
        assertDoesNotThrow(()-> read.loadPropertyFile(Constants.dbFile));
    }

    @Test
    public void loadPropertyFileNotNullTest(){
        PropertyFileReader read = new PropertyFileReader();
        assertDoesNotThrow(()-> read.loadPropertyFile(Constants.dbFile));
        assertNotNull(read.loadPropertyFile(Constants.dbFile));
    }

}
