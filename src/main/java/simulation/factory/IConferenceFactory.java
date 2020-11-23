package simulation.factory;

import simulation.model.Conference;
import simulation.model.IConference;

public interface IConferenceFactory {

    IConference newConference();

}
