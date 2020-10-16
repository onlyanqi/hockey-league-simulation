package factory;

import dao.AddSeasonDao;
import dao.LoadSeasonDao;
import simulation.data.IAddSeasonFactory;
import simulation.data.ILoadSeasonFactory;
import simulation.model.Season;

public class SeasonConcrete {

    public Season newSeason(){
        return new Season();
    }

    public ILoadSeasonFactory newLoadSeasonFactory(){
        return new LoadSeasonDao();
    }

    public IAddSeasonFactory newAddSeasonFactory(){
        return new AddSeasonDao();
    }

}
