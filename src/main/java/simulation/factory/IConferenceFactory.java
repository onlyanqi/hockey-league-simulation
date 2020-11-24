package simulation.factory;

import simulation.dao.IConferenceDao;
import simulation.model.IConference;

public interface IConferenceFactory {

    IConference newConference();

    IConferenceDao newConferenceDao();

    IConference newConferenceWithId(int id);

    IConference newConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception;
}
