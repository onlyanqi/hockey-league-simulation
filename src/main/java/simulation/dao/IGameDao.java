package simulation.dao;

import simulation.model.IGame;

import java.util.List;

public interface IGameDao {

    long addGame(int leagueId, IGame game) throws Exception;

    void loadGameById(int id, IGame game) throws Exception;

    List<IGame> loadGamesByLeagueId(int leagueId) throws Exception;

    void updateGameById(IGame game) throws Exception;
}
