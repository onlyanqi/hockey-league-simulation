package dao;

import common.Constants;
import data.IConferenceFactory;
import model.Conference;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConferenceDao implements IConferenceFactory {
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

    @Override
    public void loadConferenceByName(int id, Conference conference) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadConferenceByName);
            callDB.setInputParameterString(1, conference.getName());
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    conference.setId(rs.getInt(2));
                    conference.setName(rs.getString(3));
                    conference.setLeagueId(rs.getInt(4));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }
}
