package factory;

import dao.AddConferenceDao;
import dao.LoadConferenceDao;
import simulation.data.IAddConferenceFactory;
import simulation.data.ILoadConferenceFactory;
import simulation.model.Conference;

public class ConferenceConcrete {

    public Conference newConference(){
        return new Conference();
    }

    public ILoadConferenceFactory newLoadConferenceFactory(){
        return new LoadConferenceDao();
    }

    public IAddConferenceFactory newAddConferenceFactory(){
        return new AddConferenceDao();
    }

}
