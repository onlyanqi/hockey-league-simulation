package simulation.data;

import simulation.model.Season;

public interface ILoadSeasonFactory {

    void loadSeasonById(int id, Season season) throws Exception;

}
