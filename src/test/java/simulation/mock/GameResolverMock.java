package simulation.mock;

import db.data.IGameResolverFactory;
import db.data.ITrainingFactory;
import simulation.model.GameResolver;
import simulation.model.Training;

public class GameResolverMock implements IGameResolverFactory {


    @Override
    public long addGameResolver(int leagueId, GameResolver gameResolver) throws Exception {
        if(gameResolver == null){
            return -1;
        }else {
            loadGameResolverById(1,gameResolver);
            return gameResolver.getId();
        }
    }

    @Override
    public void loadGameResolverById(int id, GameResolver gameResolver) throws Exception {
        switch (id){
            case 1 :
                gameResolver.setRandomWinChance(0.2);
                gameResolver.setId(1);
                break;
            case 2:
                gameResolver.setRandomWinChance(0.05);
                gameResolver.setId(2);
        }
    }

    @Override
    public void loadResolverByLeagueId(int leagueId, GameResolver gameResolver) throws Exception {
        switch (leagueId){
            case 1 :
                gameResolver.setRandomWinChance(0.1);
                gameResolver.setId(3);
                break;
            case 2:
                gameResolver.setRandomWinChance(0.03);
                gameResolver.setId(4);
        }
    }
}
