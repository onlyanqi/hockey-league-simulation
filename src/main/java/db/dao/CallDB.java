package db.dao;

import db.connect.DBConnection;
import db.connect.IDBConnection;

import java.sql.*;
import java.time.LocalDate;

public class CallDB implements ICallDB {

    private String procedureName;
    private Connection connection;
    private CallableStatement stmt;

    public CallDB(String procedureName) throws Exception {
        this.procedureName = procedureName;
        IDBConnection dbConnection = new DBConnection();
        connection = dbConnection.getConnection();

        String query = "call ".concat(procedureName);
        stmt = connection.prepareCall(query);
    }

    @Override
    public void setInputParameterString(int index, String input) throws Exception {
        stmt.setString(index, input);
    }

    @Override
    public void setInputParameterInt(int index, int input) throws Exception {
        stmt.setInt(index, input);
    }

    @Override
    public void setInputParameterBoolean(int index, boolean input) throws Exception {
        stmt.setBoolean(index, input);
    }

    @Override
    public void setInputParameterDouble(int index, double input) throws Exception {
        stmt.setDouble(index, input);
    }

    @Override
    public void setInputParameterDate(int index, Date input) throws Exception {
        stmt.setDate(index,input);
    }

    @Override
    public void execute() throws Exception {
        stmt.execute();
    }

    @Override
    public ResultSet executeLoad() throws Exception {
        if (stmt.execute()) {
            return stmt.getResultSet();
        }
        return null;
    }

    @Override
    public void setOutputParameterInt(int index) throws Exception {
        stmt.registerOutParameter(index, Types.INTEGER);
    }

    @Override
    public void setOutputParameterString(int index) throws Exception {
        stmt.registerOutParameter(index, Types.VARCHAR);
    }

    @Override
    public int returnOutputParameterInt(int index) throws Exception {
        return stmt.getInt(index);
    }

    @Override
    public void setOutputParameterBoolean(int index) throws Exception {
        stmt.registerOutParameter(index, Types.BOOLEAN);
    }

    @Override
    public void setOutputParameterDouble(int index, double input) throws Exception {
        stmt.registerOutParameter(index, Types.DOUBLE);
    }

    @Override
    public void setOutputParameterDate(int index, LocalDate date) throws Exception {
        stmt.registerOutParameter(index, Types.DATE);
    }

    @Override
    public String returnOutputParameterString(int index) throws Exception {
        return stmt.getString(index);
    }

    @Override
    public Boolean returnOutputParameterBoolean(int index) throws Exception {
        return stmt.getBoolean(index);
    }

    @Override
    public LocalDate returnOutputParameterDate(int index) throws Exception {
        return stmt.getDate(index).toLocalDate();
    }

    @Override
    public void closeConnection() throws Exception {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
