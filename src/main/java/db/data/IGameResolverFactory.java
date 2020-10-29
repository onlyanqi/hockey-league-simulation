package db.data;

import simulation.model.GameResolver;

public interface IGameResolverFactory {
    long addGameResolver(int leagueId,GameResolver gameResolver) throws Exception;

    void loadGameResolverById(int id, GameResolver gameResolver) throws Exception;

    void loadResolverByLeagueId(int leagueId, GameResolver gameResolver) throws Exception;
}
