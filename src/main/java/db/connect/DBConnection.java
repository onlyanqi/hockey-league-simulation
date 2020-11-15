package db.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static DBConnection dbConnection;

    private DBConnection(){}

    public static DBConnection getInstance() {
        if (null == dbConnection) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public String formDBURL(Properties prop) {
        String dbURL = "";
        if (prop == null || prop.isEmpty()) {
            return null;
        } else {
            String dbHost = prop.getProperty("db.url");
            String dbName = prop.getProperty("db.Name");
            String dbPort = prop.getProperty("db.Port");

            dbURL = dbURL.concat("jdbc:mysql://").concat(dbHost).concat(":")
                    .concat(dbPort).concat("/").concat(dbName);
        }

        return dbURL;
    }

    public Connection getConnection() throws Exception {
        Connection con;

        try {

            IPropertyFileReader read = new PropertyFileReader();
            Properties prop = read.loadPropertyFile("../.properties");
            con = DriverManager.getConnection(formDBURL(prop),
                    prop.getProperty("db.User"), prop.getProperty("db.Password"));

            return con;
        } catch (Exception ex) {
            throw ex;
        }
    }



}
