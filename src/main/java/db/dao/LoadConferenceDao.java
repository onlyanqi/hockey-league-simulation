/*
package dao;

import common.Constants;
import simulation.data.ILoadConferenceFactory;
import simulation.model.Conference;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoadConferenceDao implements ILoadConferenceFactory {

    private ICallDB callDB = null;

    @Override
    public void loadConferenceByName(int id, Conference conference) throws Exception {
        try {
            callDB = new CallDB(Constants.loadConference);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();

            conference.setId(callDB.returnOutputParameterInt(2));
            conference.setName(callDB.returnOutputParameterString(3));
            conference.setLeagueId(callDB.returnOutputParameterInt(4));
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public Conference loadConferenceByName(String conferenceName) throws Exception {
        Conference conference = null;
        try {
            callDB = new CallDB(Constants.loadConference);
            callDB.setInputParameterString(1, conferenceName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();
            conference = new Conference();
            conference.setId(callDB.returnOutputParameterInt(2));
            conference.setName(callDB.returnOutputParameterString(3));
            conference.setLeagueId(callDB.returnOutputParameterInt(4));
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
        return conference;
    }

    @Override
    public List<Conference> loadConferenceListByLeagueId(int leagueId) throws Exception {
        List<Conference> conferenceList = null;
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.loadConferenceListByLeagueId);
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                conferenceList = new ArrayList<>();
                while (rs.next()) {
                    Conference conference = new Conference();
                    conference.setId(rs.getInt(1));
                    conference.setName(rs.getString(2));
                    conference.setLeagueId(leagueId);
                    conferenceList.add(conference);
                }
            }
        } catch (Exception e){
            throw e;
        }

        return conferenceList;
    }
}
*/
