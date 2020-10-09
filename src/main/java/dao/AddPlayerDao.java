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
            callDB.setInputParameterInt(2, player.getFreeAgentId());
            callDB.setInputParameterInt(3, player.getSeasonId());
            callDB.setInputParameterString(4, player.getName());
            callDB.setInputParameterString(5, player.getPosition());
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

}
