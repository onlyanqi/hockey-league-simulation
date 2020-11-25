package simulation.model;

import simulation.dao.IGameResolverDao;

public class GameResolver extends SharedAttributes implements IGameResolver{
    private Double randomWinChance;

    public GameResolver() {
        setId(System.identityHashCode(this));
    }

    public GameResolver(simulation.serializers.ModelsForDeserialization.model.GameResolver gameResolver){
        if(gameResolver == null){
            this.randomWinChance = 0.0;
        }else{
            this.randomWinChance = gameResolver.randomWinChance;
            this.setName(gameResolver.name);
            this.setId(gameResolver.id);
        }
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
