package simulation.dao;

import simulation.model.IPlayer;

import java.util.List;

public class PlayerDao extends DBExceptionLog implements IPlayerDao {


    @Override
    public int addPlayer(IPlayer player) throws Exception {
        return 0;
    }

    @Override
    public int addRetiredPlayer(int leagueId, IPlayer player) throws Exception {
        return 0;
    }

    @Override
    public void loadPlayerById(int id, IPlayer player) throws Exception {

    }

    @Override
    public List<IPlayer> loadPlayerListByFreeAgentId(int teamId) throws Exception {
        return null;
    }

    @Override
    public List<IPlayer> loadPlayerListByTeamId(int teamId) throws Exception {
        return null;
    }

    @Override
    public void updatePlayerById(int id, IPlayer player) throws Exception {

    }

    @Override
    public void deletePlayerListOfTeam(int teamId) throws Exception {

    }
}
