package simulation.factory;

import simulation.model.INHLEvents;

public interface INHLEventsFactory {

    INHLEvents newNHLEvents();

    INHLEvents newNHLEventsByYear(int year);

}
