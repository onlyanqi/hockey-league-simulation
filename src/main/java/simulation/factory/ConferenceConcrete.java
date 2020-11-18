package simulation.factory;

import db.data.IConferenceFactory;
import simulation.model.Conference;

public class ConferenceConcrete {

    public Conference newConference() {
        return new Conference();
    }


}
