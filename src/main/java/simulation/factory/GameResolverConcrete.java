package simulation.factory;

import simulation.model.GameResolver;

public class GameResolverConcrete {
    public GameResolver newGameResolver() {
        return new GameResolver();
    }
}
