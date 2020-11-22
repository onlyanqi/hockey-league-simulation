package simulation.factory;

import simulation.model.INHLEvents;
import simulation.model.NHLEvents;

public interface IEventFactory {

    INHLEvents newEvents();

}
