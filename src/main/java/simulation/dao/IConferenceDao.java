package simulation.dao;

import simulation.model.IConference;

import java.util.List;

public interface IConferenceDao {

    int addConference(IConference conference) throws Exception;

    void loadConferenceById(int id, IConference conference) throws Exception;

    IConference loadConferenceByName(String conferenceName) throws Exception;

    List<IConference> loadConferenceListByLeagueId(int leagueId) throws Exception;

    List formDivisionList() throws Exception;
}
