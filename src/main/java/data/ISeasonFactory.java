package data;

import model.Season;

public interface ISeasonFactory {

    int addSeason(Season season) throws Exception;
    void loadSeasonByName(int id, Season season) throws Exception;

}
