package db.dao;

import db.data.IPlayerFactory;
import simulation.model.Player;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao implements IPlayerFactory {
    @Override
    public int addPlayer(Player player) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB("AddPlayer(?,?,?,?,?,?,?)");
            callDB.setInputParameterInt(1, player.getTeamId());
            callDB.setInputParameterInt(2, player.getFreeAgentId());
            callDB.setInputParameterInt(3, player.getSeasonId());
            callDB.setInputParameterString(4, player.getName());
            callDB.setInputParameterString(5, player.getPosition().toString());
            callDB.setInputParameterBoolean(6, player.isCaptain());

            callDB.setOutputParameterInt(7);
            callDB.execute();
            player.setId(callDB.returnOutputParameterInt(7));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return player.getId();
    }

    @Override
    public void loadPlayerById(int id, Player player) throws Exception {

        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadPlayerByName(?,?,?,?,?,?,?)");
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterString(4);
            callDB.setOutputParameterBoolean(5);
            callDB.executeLoad();


            player.setId(callDB.returnOutputParameterInt(2));
            player.setName(callDB.returnOutputParameterString(3));
            Player.Position position = Player.Position.valueOf(callDB.returnOutputParameterString(4));
            player.setPosition(position);
            player.setCaptain(callDB.returnOutputParameterBoolean(5));

        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }

    @Override
    public List<Player> loadPlayerListByFreeAgentId(int freeAgentId) throws Exception {
        List<Player> playerList = null;
        ICallDB callDB = null;
        try{
            callDB = new CallDB("LoadPlayerListByFreeAgentId(?)");
            callDB.setInputParameterInt(1, freeAgentId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                playerList = new ArrayList<>();
                while (rs.next()) {
                    Player player = new Player();
                    player.setId(rs.getInt(1));
                    player.setName(rs.getString(2));
                    player.setFreeAgentId(freeAgentId);
                    playerList.add(player);
                }
            }
        } catch (Exception e){
            throw e;
        }

        return playerList;
    }

    @Override
    public List<Player> loadPlayerListByTeamId(int teamId) throws Exception {
        List<Player> playerList = null;
        ICallDB callDB = null;
        try{
            callDB = new CallDB("LoadPlayerListByTeamId(?)");
            callDB.setInputParameterInt(1, teamId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                playerList = new ArrayList<>();
                while (rs.next()) {
                    Player player = new Player();
                    player.setId(rs.getInt(1));
                    player.setName(rs.getString(2));
                    player.setTeamId(teamId);
                    playerList.add(player);
                }
            }
        } catch (Exception e){
            throw e;
        }

        return playerList;
    }

}
