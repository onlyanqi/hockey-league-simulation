package data;

import model.Player;

import java.util.List;

public interface ILoadPlayerFactory {

    void loadPlayerById(int id, Player player) throws Exception;

    List<Player> loadPlayerListByFreeAgentId(int teamId) throws Exception;

    List<Player> loadPlayerListByTeamId(int teamId) throws Exception;
}