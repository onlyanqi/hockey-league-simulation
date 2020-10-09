package data;

import model.Season;

public interface ILoadSeasonFactory {

    void loadSeasonById(int id, Season season) throws Exception;

}
