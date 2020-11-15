package db.dao;

import db.data.IDivisionFactory;
import simulation.model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DivisionDao extends DBExceptionLog implements IDivisionFactory {

    @Override
    public int addDivision(Division division) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "AddDivision(?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterString(1, division.getName());
            callDB.setInputParameterInt(2, division.getConferenceId());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            division.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException) {
            printLog("DivisionDao: addDivision: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (callDB == null) {
                return 0;
            } else {
                callDB.closeConnection();
            }
        }
        return division.getId();
    }

    @Override
    public void loadDivisionById(int id, Division division) throws Exception {
        ICallDB callDB = null;
        try {
            String procedureName = "LoadDivisionByName(?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();

            division.setId(callDB.returnOutputParameterInt(2));
            division.setName(callDB.returnOutputParameterString(3));
            division.setConferenceId(callDB.returnOutputParameterInt(4));

        } catch (SQLException sqlException) {
            printLog("DivisionDao: loadDivisionById: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (callDB == null) {
                return;
            } else{
                callDB.closeConnection();
            }
        }
    }

    @Override
    public Division loadDivisionByName(String divisionName) throws Exception {
        ICallDB callDB = null;
        Division division = null;
        try {
            String procedureName = "LoadDivisionByName(?,?,?,?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterString(1, divisionName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();

            division = new Division();
            division.setId(callDB.returnOutputParameterInt(2));
            division.setName(callDB.returnOutputParameterString(3));
            division.setConferenceId(callDB.returnOutputParameterInt(4));
        } catch (SQLException sqlException) {
            printLog("DivisionDao: loadDivisionByName: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (callDB == null) {
                return division;
            } else{
                callDB.closeConnection();
            }
        }
        return division;
    }

    @Override
    public List<Division> loadDivisionListByConferenceId(int conferenceId) throws Exception {
        List<Division> divisionList = null;
        ICallDB callDB = null;
        try {
            String procedureName = "LoadDivisionListByConferenceId(?)";
            callDB = new CallDB(procedureName);
            callDB.setInputParameterInt(1, conferenceId);
            ResultSet rs = callDB.executeLoad();
            if (rs == null) {
                return divisionList;
            } else{
                divisionList = new ArrayList<>();
                while (rs.next()) {
                    Division division = new Division();
                    division.setId(rs.getInt(1));
                    division.setName(rs.getString(2));
                    division.setConferenceId(conferenceId);
                    divisionList.add(division);
                }
            }
        } catch (SQLException sqlException) {
            printLog("DivisionDao: loadDivisionListByConferenceId: SQLException: " + sqlException);
            throw sqlException;
        } finally {
            if (callDB == null) {
                return divisionList;
            } else {
                callDB.closeConnection();
            }
        }

        return divisionList;
    }

}
