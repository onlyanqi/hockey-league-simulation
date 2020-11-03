package db.data;

import simulation.model.Game;

import java.util.List;

public interface IGameFactory {
    long addGame(int leagueId, Game game) throws Exception;

    void loadGameById(int id, Game game) throws Exception;

    List<Game> loadGamesByLeagueId(int leagueId) throws Exception;

    void updateGameById(Game game) throws Exception;
}
