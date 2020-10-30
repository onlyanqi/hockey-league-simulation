package simulation.factory;

import db.dao.EventDao;
import db.dao.TeamStandingDao;
import db.data.IEventFactory;
import db.data.ITeamStandingFactory;
import simulation.model.NHLEvents;
import simulation.model.TeamStanding;

public class EventConcrete {

    public NHLEvents newEvents() {
        return new NHLEvents();
    }

    public IEventFactory newLoadEvents() {
        return new EventDao();
    }

    public IEventFactory newAddEventsFactory() {
        return new EventDao();
    }
}
