package data;

import model.Player;

public interface IPlayerFactory {

    int addPlayer(Player player) throws Exception;
    void loadPlayerByName(int id, Player player) throws Exception;

}
