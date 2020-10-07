package dao;

import common.Constants;
import data.IPlayerFactory;
import model.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDao implements IPlayerFactory {
    @Override
    public int addPlayer(Player player) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.addPlayer);
            callDB.setInputParameterInt(1, player.getTeamId());
            callDB.setInputParameterInt(2, player.getSeasonId());
            callDB.setInputParameterString(3, player.getName());
            callDB.setInputParameterString(4, player.getPosition());
            callDB.setInputParameterBoolean(5, player.isCaptain());

            callDB.setOutputParameterInt(6);
            callDB.execute();
            player.setId(callDB.returnOutputParameterInt(6));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return player.getId();
    }

    @Override
    public void loadPlayerByName(int id, Player player) throws Exception {

        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadPlayerByName);
            callDB.setInputParameterString(1, player.getName());
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.setOutputParameterString(4);
            callDB.setOutputParameterBoolean(5);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    player.setId(rs.getInt(2));
                    player.setName(rs.getString(3));
                    player.setPosition(rs.getString(4));
                    player.setCaptain(rs.getBoolean(5));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }
    }
}
