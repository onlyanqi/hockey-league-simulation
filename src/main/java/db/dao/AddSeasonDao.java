package dao;

import common.Constants;
import simulation.data.IAddSeasonFactory;
import simulation.model.Season;

import java.sql.SQLException;

public class AddSeasonDao implements IAddSeasonFactory {
    @Override
    public int addSeason(Season season) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.addSeason);
            callDB.setInputParameterString(1, season.getName());
            callDB.setOutputParameterInt(2);
            callDB.execute();
            season.setId(callDB.returnOutputParameterInt(2));

        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return season.getId();
    }

}
