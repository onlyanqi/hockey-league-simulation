package model;

import simulation.data.IAddSeasonFactory;
import simulation.model.Season;

public class AddSeasonMock implements IAddSeasonFactory {

    @Override
    public int addSeason(Season season) throws Exception {
        season = new Season(1);
        return season.getId();
    }
}
