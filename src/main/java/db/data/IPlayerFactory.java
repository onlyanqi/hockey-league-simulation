package db.data;

import simulation.model.Player;

import java.util.List;

public interface IPlayerFactory {

    int addPlayer(Player player) throws Exception;

    void loadPlayerById(int id, Player player) throws Exception;

    List<Player> loadPlayerListByFreeAgentId(int teamId) throws Exception;

    List<Player> loadPlayerListByTeamId(int teamId) throws Exception;

    void updatePlayerById(int id, Player player) throws  Exception;

}
