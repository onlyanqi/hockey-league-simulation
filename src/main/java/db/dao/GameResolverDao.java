package db.dao;

import db.data.IGameResolverDao;
import simulation.model.IGameResolver;

public class GameResolverDao extends DBExceptionLog implements IGameResolverDao {

    @Override
    public long addGameResolver(int leagueId, IGameResolver gameResolver) throws Exception {
        return 0;
    }

    @Override
    public void loadGameResolverById(int id, IGameResolver gameResolver) throws Exception {

    }

    @Override
    public void loadResolverByLeagueId(int leagueId, IGameResolver gameResolver) throws Exception {

    }

}
