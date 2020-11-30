package persistance.dao;

import simulation.model.IPlayer;

import java.util.List;

public interface IPlayerDao {

    int addPlayer(IPlayer player) throws Exception;

    int addRetiredPlayer(int leagueId, IPlayer player) throws Exception;

    void loadPlayerById(int id, IPlayer player) throws Exception;

    List<IPlayer> loadPlayerListByFreeAgentId(int teamId) throws Exception;

    List<IPlayer> loadPlayerListByTeamId(int teamId) throws Exception;

    void updatePlayerById(int id, IPlayer player) throws Exception;

    void deletePlayerListOfTeam(int teamId) throws Exception;

}
