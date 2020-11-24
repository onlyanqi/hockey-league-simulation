package simulation.dao;

import simulation.model.IConference;

import java.util.List;

public class ConferenceDao extends DBExceptionLog implements IConferenceDao {

    @Override
    public int addConference(IConference conference) throws Exception {
        return 0;
    }

    @Override
    public void loadConferenceById(int id, IConference conference) throws Exception {

    }

    @Override
    public IConference loadConferenceByName(String conferenceName) throws Exception {
        return null;
    }

    @Override
    public List<IConference> loadConferenceListByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public List formDivisionList() throws Exception {
        return null;
    }

}
