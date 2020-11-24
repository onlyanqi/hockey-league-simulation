package simulation.dao;

import simulation.model.IGame;

import java.util.List;

public class GameDao extends DBExceptionLog implements IGameDao {

    @Override
    public long addGame(int leagueId, IGame game) throws Exception {
        return 0;
    }

    @Override
    public void loadGameById(int id, IGame game) throws Exception {


    }

    @Override
    public List<IGame> loadGamesByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public void updateGameById(IGame game) throws Exception {

    }

}
