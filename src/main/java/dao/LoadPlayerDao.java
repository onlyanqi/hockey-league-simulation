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
            callDB.executeLoad();

            player = new Player();
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
}
