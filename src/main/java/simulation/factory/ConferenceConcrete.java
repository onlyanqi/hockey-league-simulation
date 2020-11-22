package simulation.factory;

import simulation.model.Conference;
import simulation.model.IConference;

public class ConferenceConcrete implements IConferenceFactory{

    public IConference newConference() {
        return new Conference();
    }


}
