package dao;

import common.Constants;
import data.IAddLeagueFactory;
import data.ILoadLeagueFactory;
import model.League;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddLeagueDao implements IAddLeagueFactory {

    @Override
    public int addLeague(League league) throws Exception{
        ICallDB callDB = null;
        try{
            callDB = new CallDB(Constants.addLeague);
            callDB.setInputParameterString(1, league.getName());
            callDB.setInputParameterInt(2, league.getCreatedBy());
            callDB.setOutputParameterInt(3);
            callDB.execute();
            league.setId(callDB.returnOutputParameterInt(3));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return league.getId();
    }


}
