package factory;

import dao.AddConferenceDao;
import dao.LoadConferenceDao;
import data.IAddConferenceFactory;
import data.ILoadConferenceFactory;
import model.Conference;

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
