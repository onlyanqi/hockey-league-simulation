package simulation.model.mock;

import db.data.ISeasonFactory;
import simulation.model.Season;

public class SeasonMock implements ISeasonFactory {

    @Override
    public int addSeason(Season season) throws Exception {
        season = new Season(1);
        return season.getId();
    }

    @Override
    public void loadSeasonById(int id, Season season) {

        switch (new Long(id).intValue()) {
            case 1:
                //all correct data
                season.setId(1);
                season.setName("Season1");
                break;

            case 2:
                //name null
                season.setId(2);
                season.setName(null);
                break;

        }

    }

}
