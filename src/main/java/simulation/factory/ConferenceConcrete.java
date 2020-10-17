package simulation.factory;

import db.dao.ConferenceDao;
import db.data.IConferenceFactory;
import simulation.model.Conference;

public class ConferenceConcrete {

    public Conference newConference(){
        return new Conference();
    }

    public IConferenceFactory newLoadConferenceFactory(){
        return new ConferenceDao();
    }

    public IConferenceFactory newAddConferenceFactory(){
        return new ConferenceDao();
    }

}
