package simulation.factory;

import simulation.model.IPlayer;
import simulation.model.Player;

public class PlayerConcrete implements IPlayerFactory {

    public IPlayer newPlayer() {
        return new Player();
    }

}
