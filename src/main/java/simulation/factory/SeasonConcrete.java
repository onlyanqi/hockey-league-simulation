package simulation.factory;

import db.data.ISeasonFactory;
import simulation.model.Season;

public class SeasonConcrete {

    public Season newSeason() {
        return new Season();
    }


}
