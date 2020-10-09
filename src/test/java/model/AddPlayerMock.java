package model;

import data.IAddPlayerFactory;
import data.ILoadPlayerFactory;

import java.util.Date;
import java.util.List;

public class AddPlayerMock implements IAddPlayerFactory {

    @Override
    public int addPlayer(Player player) throws Exception {
        player = new Player(1);
        return player.getId();
    }
}
