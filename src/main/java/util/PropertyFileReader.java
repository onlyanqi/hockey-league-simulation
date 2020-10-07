package util;

import common.Constants;

import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader implements IPropertyFileReader{

    public Properties loadPropertyFile(String fileName) {
        Properties prop = null;

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream(fileName);
            prop = new Properties();
            prop.load(inputStream);
        } catch (Exception e){
            throw new IllegalArgumentException("File Not Found Exception in ProperfileReader for "+fileName);
        }

        return prop;
    }

}
