package db.dao;

import common.Constants;
import db.data.IDivisionFactory;
import simulation.model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public void loadDivisionById(int id, Division division) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadDivisionByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();



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

    @Override
    public List<Division> loadDivisionListByConferenceId(int conferenceId) throws Exception {
        List<Division> divisionList = null;
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.loadDivisionListByConferenceId);
            callDB.setInputParameterInt(1, conferenceId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                divisionList = new ArrayList<>();
                while (rs.next()) {
                    Division division = new Division();
                    division.setId(rs.getInt(1));
                    division.setName(rs.getString(2));
                    division.setConferenceId(conferenceId);
                    divisionList.add(division);
                }
            }
        } catch (Exception e){
            throw e;
        }

        return divisionList;
    }

}
