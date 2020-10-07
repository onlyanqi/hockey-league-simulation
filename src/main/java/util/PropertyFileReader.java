package util;

import java.io.*;
import java.util.Properties;

public class PropertyFileReader implements IPropertyFileReader{

    /*public Properties loadPropertyFile(String fileName) {
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
    }*/

    @Override
    public Properties loadPropertyFile(String fileName) throws Exception {
        Properties prop = null;
        String path = "./.properties";
        FileInputStream file = null;
        try {
            file = new FileInputStream(path);
            prop = new Properties();
            prop.load(file);
        } catch (FileNotFoundException e){
            throw new IllegalArgumentException("File Not Found Exception in ProperfileReader for "+fileName,e);
        } catch (IOException e){
            throw new IllegalArgumentException("IOException in ProperfileReader for "+fileName,e);
        } catch (Exception e){
            throw new IllegalArgumentException("Exception in ProperfileReader for "+fileName,e);
        }
        finally {
            if(file != null) {
                try {
                    file.close();
                } catch (IOException e){
                    throw new IllegalArgumentException("IOException in ProperfileReader for "+fileName);
                }
            }
        }

        return prop;
    }
}
