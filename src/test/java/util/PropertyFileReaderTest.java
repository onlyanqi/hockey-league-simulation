package util;

import common.Constants;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class PropertyFileReaderTest {

    @Test(expected = Test.None.class)
    public void loadPropertyFileNoExceptionTest(){
        PropertyFileReader read = new PropertyFileReader();
        read.loadPropertyFile(Constants.dbFile);
    }

    @Test(expected = Test.None.class)
    public void loadPropertyFileNotNullTest(){
        PropertyFileReader read = new PropertyFileReader();
        read.loadPropertyFile(Constants.dbFile);
        assertNotNull(read.loadPropertyFile(Constants.dbFile));
    }

}
