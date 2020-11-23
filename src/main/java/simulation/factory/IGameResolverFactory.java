package simulation.factory;

import simulation.model.GameResolver;
import simulation.model.IGameResolver;

public interface IGameResolverFactory {

    IGameResolver newGameResolver();

}
