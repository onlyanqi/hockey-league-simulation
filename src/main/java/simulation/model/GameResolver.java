package simulation.model;

import db.data.IGameResolverDao;

public class GameResolver extends SharedAttributes implements IGameResolver{
    private Double randomWinChance;

    public GameResolver() {
        setId(System.identityHashCode(this));
    }

    public GameResolver(int leagueId, IGameResolverDao gameResolverFactory) throws Exception {
        if (gameResolverFactory == null) {
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
