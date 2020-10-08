package dao;

import common.Constants;
import data.ILoadConferenceFactory;
import model.Conference;

import java.sql.ResultSet;
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
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    conference = new Conference();
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

    @Override
    public Conference loadConferenceByName(String conferenceName) throws Exception {
        Conference conference = null;
        try {
            callDB = new CallDB(Constants.loadConference);
            callDB.setInputParameterString(1, conferenceName);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterInt(4);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    conference = new Conference();
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
        return conference;
    }

    public List<Conference> loadConferenceListByLeagueId(int leagueId) throws Exception {
        List<Conference> conferenceList = null;
        try{


        } catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }


        return conferenceList;
    }
}
