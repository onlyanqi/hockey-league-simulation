package dao;

import java.sql.ResultSet;

public interface ICallDB {

    void setInputParameterString(int index, String input) throws Exception;
    void setInputParameterInt(int index, int input) throws Exception;
    void execute() throws Exception ;
    void closeConnection() throws Exception;
    void setOutputParameterInt(int index) throws Exception;
    int returnOutputParameterInt(int index) throws Exception;
    ResultSet executeLoad() throws Exception;

}
