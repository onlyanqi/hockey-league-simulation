package dao;

import common.Constants;
import data.ILoadDivisionFactory;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadDivisionDao implements ILoadDivisionFactory {

    @Override
    public void loadDivisionById(int id, Division division) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadDivisionByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();


                    division = new Division();
                    division.setId(callDB.returnOutputParameterInt(2));
                    division.setName(callDB.returnOutputParameterString(3));
                    division.setConferenceId(callDB.returnOutputParameterInt(4));

        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public Division loadDivisionByName(String divisionName) throws Exception {
        ICallDB callDB = null;
        Division division = null;
        try {
            callDB = new CallDB(Constants.loadDivisionByName);
            callDB.setInputParameterString(1, divisionName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();


            division = new Division();
            division.setId(callDB.returnOutputParameterInt(2));
            division.setName(callDB.returnOutputParameterString(3));
            division.setConferenceId(callDB.returnOutputParameterInt(4));
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
        return division;
    }
}
