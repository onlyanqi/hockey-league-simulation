package db.data;

import simulation.model.GamePlayConfig;

public interface IGamePlayConfigDao {

    void loadGamePlayConfigByLeagueId(int leagueId, GamePlayConfig gamePlayConfig) throws Exception;

}
