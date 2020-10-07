package dao.connect;

import common.Constants;
import util.IPropertyFileReader;
import util.PropertyFileReader;

import java.sql.*;
import java.util.Properties;

public class DBConnection implements IDBConnection {

    public String formDBURL(Properties prop){
        String dbURL = "";

        if(prop != null) {
            String dbHost = prop.getProperty(Constants.dbHost);
            String dbName = prop.getProperty(Constants.dbName);
            String dbPort = prop.getProperty(Constants.dbPort);

            dbURL = dbURL.concat("jdbc:mysql://").concat(dbHost).concat(Constants.semiColon)
                    .concat(dbPort).concat(Constants.forwardSlash).concat(dbName)
                    .concat(Constants.timezone);
            System.out.println(dbURL);
        }

        return dbURL;
    }


    public Connection getConnection() throws Exception {
        Connection con = null;

        try {

            IPropertyFileReader read = new PropertyFileReader();
            Properties prop = read.loadPropertyFile(Constants.dbFile);
            con = DriverManager.getConnection(formDBURL(prop),
                    prop.getProperty(Constants.dbUserName),prop.getProperty(Constants.dbPassword));

            return con;

        } catch (Exception ex) {
            throw ex;
        }

    }

    /*public static void main(String args[]){
        DBConnection con = new DBConnection();

        try{

            Connection connection = con.getConnection();
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Conference;");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

        } catch (Exception e){
            System.out.println("main method: "+ e);
            e.printStackTrace();
        }
    }*/

}
