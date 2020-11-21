package simulation.factory;

import simulation.model.GamePlayConfig;
import simulation.model.IGamePlayConfig;

public class GamePlayConfigConcrete implements IGamePlayConfigFactory{

    public IGamePlayConfig newGamePlayConfig() {
        return new GamePlayConfig();
    }

}
