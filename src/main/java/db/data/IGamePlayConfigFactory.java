package db.data;

import simulation.model.GamePlayConfig;

public interface IGamePlayConfigFactory {

    void loadGamePlayConfigByLeagueId(int leagueId, GamePlayConfig gamePlayConfig) throws Exception;

}
