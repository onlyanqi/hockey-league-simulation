package dao.connect;

import common.Constants;
import util.IPropertyFileReader;
import util.PropertyFileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection implements IDBConnection {

    private Properties prop;

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public DBConnection(){
        setProp(new Properties());
    }

    public String formDBURL(Properties prop){
        String dbURL = "";

        if(prop != null) {
            String dbHost = prop.getProperty(Constants.dbHost);
            String dbName = prop.getProperty(Constants.dbName);
            String dbPort = prop.getProperty(Constants.dbPort);

            dbURL = dbURL.concat("jdbc:mysql://").concat(dbHost).concat(Constants.semiColon)
                    .concat(dbPort).concat(Constants.forwardSlash).concat(dbName);
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
            ResultSet rs=stmt.executeQuery("select * from directors;");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

        } catch (Exception e){
            System.out.println("main method: "+ e);
            e.printStackTrace();
        }
    }*/

}
