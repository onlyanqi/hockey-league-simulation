package data;

import model.Player;

public interface ILoadPlayerFactory {

    void loadPlayerById(int id, Player player) throws Exception;

}
