package db.connect;

import util.IPropertyFileReader;
import util.PropertyFileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection implements IDBConnection {

    public String formDBURL(Properties prop){
        String dbURL = "";

        if(prop != null) {
            String dbHost = prop.getProperty("db.url");
            String dbName = prop.getProperty("db.Name");
            String dbPort = prop.getProperty("db.Port");

            dbURL = dbURL.concat("jdbc:mysql://").concat(dbHost).concat(";")
                    .concat(dbPort).concat("/").concat(dbName);
        }

        return dbURL;
    }


    public Connection getConnection() throws Exception {
        Connection con = null;

        try {

            IPropertyFileReader read = new PropertyFileReader();
            Properties prop = read.loadPropertyFile("../.properties");
            con = DriverManager.getConnection(formDBURL(prop),
                    prop.getProperty("db.User"),prop.getProperty("db.Password"));

            return con;
        } catch (Exception ex) {
            throw ex;
        }

    }

}
