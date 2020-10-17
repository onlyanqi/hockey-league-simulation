package db.data;

import simulation.model.Season;

public interface ISeasonFactory {

    int addSeason(Season season) throws Exception;
    void loadSeasonById(int id, Season season) throws Exception;
}
