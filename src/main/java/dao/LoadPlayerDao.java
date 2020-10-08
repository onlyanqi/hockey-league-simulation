package dao;

import common.Constants;
import data.ILoadPlayerFactory;
import model.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

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
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    player = new Player();
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
