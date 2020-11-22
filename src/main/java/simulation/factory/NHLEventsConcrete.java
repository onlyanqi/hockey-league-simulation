package simulation.factory;

import simulation.model.INHLEvents;
import simulation.model.NHLEvents;

public class NHLEventsConcrete implements INHLEventsFactory {

    @Override
    public INHLEvents newNHLEvents() {
        return new NHLEvents();
    }

}
