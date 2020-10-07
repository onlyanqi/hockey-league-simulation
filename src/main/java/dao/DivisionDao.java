package dao;

import common.Constants;
import data.IDivisionFactory;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionDao implements IDivisionFactory {
    @Override
    public int addDivision(Division division) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.addDivision);
            callDB.setInputParameterString(1, division.getName());
            callDB.setInputParameterInt(2, division.getConferenceId());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            division.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return division.getId();
    }

    @Override
    public void loadDivisionByName(int id, Division division) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadDivisionByName);
            callDB.setInputParameterString(1, division.getName());
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    division.setId(rs.getInt(2));
                    division.setName(rs.getString(3));
                    division.setConferenceId(rs.getInt(4));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }
}
