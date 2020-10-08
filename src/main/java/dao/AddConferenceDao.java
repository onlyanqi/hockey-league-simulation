package dao;

import common.Constants;
import data.IAddConferenceFactory;
import data.ILoadConferenceFactory;
import model.Conference;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddConferenceDao implements IAddConferenceFactory {
    @Override
    public int addConference(Conference conference) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.addConference);
            callDB.setInputParameterString(1, conference.getName());
            callDB.setInputParameterInt(2, conference.getLeagueId());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            conference.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return conference.getId();
    }
}
