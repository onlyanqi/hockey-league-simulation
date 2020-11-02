package simulation.factory;

import db.dao.EventDao;
import db.data.IEventFactory;
import simulation.model.NHLEvents;

public class EventConcrete {

    public NHLEvents newEvents() {
        return new NHLEvents();
    }

    public IEventFactory newAddEventsFactory() {
        return new EventDao();
    }
}
