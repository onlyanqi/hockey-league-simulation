package data;

import model.Conference;

public interface IConferenceFactory {

    int addConference(Conference conference) throws Exception;
    void loadConferenceByName(int id, Conference conference) throws Exception;

}
