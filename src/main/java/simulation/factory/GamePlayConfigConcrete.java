package simulation.factory;

import simulation.model.GamePlayConfig;

public class GamePlayConfigConcrete {
    public GamePlayConfig newGamePlayConfig() {
        return new GamePlayConfig();
    }
}
