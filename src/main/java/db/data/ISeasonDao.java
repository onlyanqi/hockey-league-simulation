package db.data;

import simulation.model.ISeason;
import simulation.model.Season;

public interface ISeasonDao {

    int addSeason(ISeason season) throws Exception;

    void loadSeasonById(int id, ISeason season) throws Exception;
}
