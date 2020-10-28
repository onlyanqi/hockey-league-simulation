package simulation.factory;

import db.dao.SeasonDao;
import db.data.ISeasonFactory;
import simulation.model.Season;

public class SeasonConcrete {

    public Season newSeason() {
        return new Season();
    }

    public ISeasonFactory newLoadSeasonFactory() {
        return new SeasonDao();
    }

    public ISeasonFactory newAddSeasonFactory() {
        return new SeasonDao();
    }

}
