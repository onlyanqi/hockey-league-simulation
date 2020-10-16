package dao;

import common.Constants;
import simulation.data.ILoadPlayerFactory;
import simulation.model.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoadPlayerDao implements ILoadPlayerFactory {

    @Override
    public void loadPlayerById(int id, Player player) throws Exception {

        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadPlayerByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterString(4);
            callDB.setOutputParameterBoolean(5);
            callDB.executeLoad();


            player.setId(callDB.returnOutputParameterInt(2));
            player.setName(callDB.returnOutputParameterString(3));
            player.setPosition(callDB.returnOutputParameterString(4));
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
            callDB = new CallDB(Constants.loadPlayerListByFreeAgentId);
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
            callDB = new CallDB(Constants.loadPlayerListByTeamId);
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
