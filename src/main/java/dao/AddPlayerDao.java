package dao;

import common.Constants;
import data.IAddPlayerFactory;
import data.ILoadPlayerFactory;
import model.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddPlayerDao implements IAddPlayerFactory {
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
            callDB.setInputParameterInt(6, player.getFreeAgentId());


            callDB.setOutputParameterInt(7);
            callDB.execute();
            player.setId(callDB.returnOutputParameterInt(6));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return player.getId();
    }

}
