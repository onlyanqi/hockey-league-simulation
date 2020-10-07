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

//            dbURL = dbURL.concat("jdbc:mysql://").concat(dbHost).concat(Constants.semiColon)
//                    .concat(dbPort).concat(Constants.forwardSlash).concat(dbName)
//                    .concat(Constants.timezone);

            dbURL = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_7_DEVINT";
        }

        return dbURL;
    }


    public Connection getConnection() throws Exception {
        Connection con = null;

        try {

            Class.forName("com.mysql.jdbc.Driver");

            IPropertyFileReader read = new PropertyFileReader();
            Properties prop = read.loadPropertyFile(Constants.dbFile);
//            con = DriverManager.getConnection("jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_7_DEVINT",
//                    "CSCI5308_7_DEVINT_USER","kzXm42YBRy");

            con = DriverManager.getConnection("jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_7_DEVINT?" + "user=CSCI5308_7_DEVINT_USER&password=kzXm42YBRy");

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
