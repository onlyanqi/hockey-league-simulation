package persistance.dao;

import simulation.model.GamePlayConfig;

public interface IGamePlayConfigDao {

    void loadGamePlayConfigByLeagueId(int leagueId, GamePlayConfig gamePlayConfig) throws Exception;

}
