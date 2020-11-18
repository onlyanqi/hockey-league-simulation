package simulation.factory;

import db.data.IPlayerFactory;
import simulation.model.Player;

public class PlayerConcrete {

    public Player newPlayer() {
        return new Player();
    }

}
