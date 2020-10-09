package factory;

import dao.AddSeasonDao;
import dao.AddUserDao;
import dao.LoadSeasonDao;
import dao.LoadUserDao;
import data.IAddSeasonFactory;
import data.IAddUserFactory;
import data.ILoadSeasonFactory;
import data.ILoadUserFactory;
import model.Season;
import model.User;

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
