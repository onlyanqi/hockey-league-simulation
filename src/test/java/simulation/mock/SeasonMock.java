package simulation.mock;

import simulation.dao.ISeasonDao;
import simulation.model.ISeason;
import simulation.model.Season;

public class SeasonMock implements ISeasonDao {

    @Override
    public int addSeason(ISeason season) throws Exception {
        season = new Season(1);
        return season.getId();
    }

    @Override
    public void loadSeasonById(int id, ISeason season) {

        switch (new Long(id).intValue()) {
            case 1:
                season.setId(1);
                season.setName("Season1");
                break;

            case 2:
                season.setId(2);
                season.setName(null);
                break;

        }

    }

}
