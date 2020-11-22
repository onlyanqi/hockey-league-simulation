package simulation.factory;

import simulation.model.INHLEvents;
import simulation.model.NHLEvents;

public class EventConcrete {

    public INHLEvents newEvents() {
        return new NHLEvents();
    }

}
