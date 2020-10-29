package db.data;

import simulation.model.Game;

public interface IGameFactory {
    long addGame(int leagueId, Game game) throws Exception;

    void loadGameById(int id, Game game) throws Exception;

    void loadGameByLeagueId(int leagueId, Game game) throws Exception;
}
