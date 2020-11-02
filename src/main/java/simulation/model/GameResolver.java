package simulation.model;

import db.data.IGameResolverFactory;

public class GameResolver extends SharedAttributes{
    private Double randomWinChance;

    public GameResolver() {
    }

    public GameResolver(int leagueId, IGameResolverFactory gameResolverFactory) throws Exception {
        if(gameResolverFactory == null){
            return;
        }
        gameResolverFactory.loadResolverByLeagueId(leagueId, this);
    }


    public Double getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(double randomWinChance) {
        this.randomWinChance = randomWinChance;
    }
}
