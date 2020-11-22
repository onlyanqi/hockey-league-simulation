package simulation.factory;

import simulation.model.GameResolver;
import simulation.model.IGameResolver;

public class GameResolverConcrete {

    public IGameResolver newGameResolver() {
        return new GameResolver();
    }

}
