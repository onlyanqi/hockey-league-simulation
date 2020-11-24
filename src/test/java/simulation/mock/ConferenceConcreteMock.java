package simulation.mock;

import simulation.dao.IConferenceDao;
import simulation.factory.IConferenceFactory;
import simulation.model.Conference;
import simulation.model.IConference;

public class ConferenceConcreteMock implements IConferenceFactory {

    public IConference newConference() {
        return new Conference();
    }

    public IConferenceDao newConferenceDao(){
        return new ConferenceMock();
    }

    public IConference newConferenceWithId(int id){
        return new Conference(id);
    }

    public IConference newConferenceWithIdDao(int id, IConferenceDao conferenceDao) throws Exception {
        return new Conference(id, conferenceDao);
    }

}
