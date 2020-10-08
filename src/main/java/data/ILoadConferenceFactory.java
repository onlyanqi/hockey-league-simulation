package data;

import model.Conference;

public interface ILoadConferenceFactory {

    void loadConferenceByName(int id, Conference conference) throws Exception;
    Conference loadConferenceByName(String conferenceName) throws Exception;

}
