package model;

import data.IAddSeasonFactory;
import data.ILoadSeasonFactory;

public class AddSeasonMock implements IAddSeasonFactory {

    @Override
    public int addSeason(Season season) throws Exception {
        season = new Season(1);
        return season.getId();
    }
}
