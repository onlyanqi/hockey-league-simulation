package dao;

import common.Constants;
import simulation.data.IAddDivisionFactory;
import simulation.model.Division;

import java.sql.SQLException;

public class AddDivisionDao implements IAddDivisionFactory {
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

}
