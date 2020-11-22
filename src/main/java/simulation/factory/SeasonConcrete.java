package simulation.factory;

import simulation.model.ISeason;
import simulation.model.Season;

public class SeasonConcrete {

    public ISeason newSeason() {
        return new Season();
    }


}
