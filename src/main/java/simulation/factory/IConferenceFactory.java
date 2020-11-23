package simulation.factory;

import db.data.IConferenceDao;
import simulation.model.Conference;
import simulation.model.IConference;

public interface IConferenceFactory {

    IConference newConference();

    IConferenceDao newConferenceDao();

    IConference newConferenceWithId(int id);

    IConference newConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception;
}
