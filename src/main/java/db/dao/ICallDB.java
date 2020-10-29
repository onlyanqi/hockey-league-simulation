package db.dao;

import java.sql.Date;
import scala.tools.nsc.backend.icode.Members;

import java.sql.ResultSet;
import java.time.LocalDate;

public interface ICallDB {

    void setInputParameterString(int index, String input) throws Exception;

    void setInputParameterInt(int index, int input) throws Exception;

    void setInputParameterBoolean(int index, boolean input) throws Exception;

    void setInputParameterDouble(int index, double input) throws Exception;

    void setInputParameterDate(int index, Date input) throws Exception;

    void execute() throws Exception;

    String returnOutputParameterString(int index) throws Exception;

    Boolean returnOutputParameterBoolean(int index) throws Exception;

    LocalDate returnOutputParameterDate(int index) throws Exception;

    void closeConnection() throws Exception;

    void setOutputParameterInt(int index) throws Exception;

    void setOutputParameterString(int index) throws Exception;

    void setOutputParameterBoolean(int index) throws Exception;

    void setOutputParameterDouble(int index, double input) throws Exception;

    void setOutputParameterDate(int index, LocalDate date) throws Exception;

    int returnOutputParameterInt(int index) throws Exception;

    //    String returnOutputParameterString(int index) throws Exception;
    ResultSet executeLoad() throws Exception;

}
