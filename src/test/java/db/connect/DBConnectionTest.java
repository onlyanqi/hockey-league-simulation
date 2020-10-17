package db.connect;

import common.Constants;
import org.junit.Test;
import java.util.Properties;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class DBConnectionTest {

    @Test
    public void formDBURLNotNullTest(){
        Properties prop = new Properties();
        prop.setProperty(Constants.dbHost, "localhost");
        prop.setProperty(Constants.dbName, "simulation/data");
        prop.setProperty(Constants.dbPort, "3306");

        IDBConnection con = new DBConnection();
        assertNotNull(con.formDBURL(prop));
    }

    @Test
    public void formDBURLNotEmptyTest(){
        Properties prop = new Properties();
        prop.setProperty(Constants.dbHost, "localhost");
        prop.setProperty(Constants.dbName, "simulation/data");
        prop.setProperty(Constants.dbPort, "3306");

        IDBConnection con = new DBConnection();
        assertNotEquals("", con.formDBURL(prop));
    }

}
