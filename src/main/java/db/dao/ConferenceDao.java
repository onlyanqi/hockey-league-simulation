package db.dao;

import db.data.IConferenceFactory;
import simulation.model.Conference;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDao implements IConferenceFactory {

    @Override
    public int addConference(Conference conference) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddConference(?,?,?)");
            callDB.setInputParameterString(1, conference.getName());
            callDB.setInputParameterInt(2, conference.getLeagueId());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            conference.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException) {
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
            callDB = new CallDB("LoadConferenceByName(?,?,?,?)");
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();

            conference.setId(callDB.returnOutputParameterInt(2));
            conference.setName(callDB.returnOutputParameterString(3));
            conference.setLeagueId(callDB.returnOutputParameterInt(4));
        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public Conference loadConferenceByName(String conferenceName) throws Exception {
        ICallDB callDB = null;
        Conference conference = null;
        try {
            callDB = new CallDB("LoadConferenceByName(?,?,?,?)");
            callDB.setInputParameterString(1, conferenceName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            callDB.executeLoad();
            conference = new Conference();
            conference.setId(callDB.returnOutputParameterInt(2));
            conference.setName(callDB.returnOutputParameterString(3));
            conference.setLeagueId(callDB.returnOutputParameterInt(4));
        } catch (Exception e) {
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
        try {
            callDB = new CallDB("LoadConferenceListByLeagueId(?)");
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
        } catch (Exception e) {
            throw e;
        }

        return conferenceList;
    }
}
