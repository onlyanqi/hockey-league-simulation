package simulation.factory;

import db.data.IEventFactory;
import simulation.model.NHLEvents;

public class EventConcrete {

    public NHLEvents newEvents() {
        return new NHLEvents();
    }

}
