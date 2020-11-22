package simulation.mock;

import db.data.IGameResolverDao;
import simulation.model.GameResolver;
import simulation.model.IGameResolver;

public class GameResolverMock implements IGameResolverDao {


    @Override
    public long addGameResolver(int leagueId, IGameResolver gameResolver) throws Exception {
        if (gameResolver == null) {
            return -1;
        } else {
            loadGameResolverById(1, gameResolver);
            return gameResolver.getId();
        }
    }

    @Override
    public void loadGameResolverById(int id, IGameResolver gameResolver) throws Exception {
        switch (id) {
            case 1:
                gameResolver.setRandomWinChance(0.2);
                gameResolver.setId(1);
                break;
            case 2:
                gameResolver.setRandomWinChance(0.05);
                gameResolver.setId(2);
        }
    }

    @Override
    public void loadResolverByLeagueId(int leagueId, IGameResolver gameResolver) throws Exception {
        switch (leagueId) {
            case 1:
                gameResolver.setRandomWinChance(0.1);
                gameResolver.setId(3);
                break;
            case 2:
                gameResolver.setRandomWinChance(0.03);
                gameResolver.setId(4);
        }
    }
}
