package simulation.data;

import simulation.model.Conference;

import java.util.List;

public interface ILoadConferenceFactory {

    void loadConferenceByName(int id, Conference conference) throws Exception;
    Conference loadConferenceByName(String conferenceName) throws Exception;

    List<Conference> loadConferenceListByLeagueId(int leagueId) throws Exception;
}
