package model;

import simulation.data.IAddPlayerFactory;
import simulation.model.Player;

public class AddPlayerMock implements IAddPlayerFactory {

    @Override
    public int addPlayer(Player player) throws Exception {
        player = new Player(1);
        return player.getId();
    }
}
