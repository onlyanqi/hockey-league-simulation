package simulation.dao;

import simulation.model.IGameResolver;

public interface IGameResolverDao {

    long addGameResolver(int leagueId, IGameResolver gameResolver) throws Exception;

    void loadGameResolverById(int id, IGameResolver gameResolver) throws Exception;

    void loadResolverByLeagueId(int leagueId, IGameResolver gameResolver) throws Exception;
}
