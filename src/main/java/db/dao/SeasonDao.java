package db.dao;

import db.data.ISeasonFactory;
import simulation.model.Season;

import java.sql.SQLException;

public class SeasonDao implements ISeasonFactory {
    @Override
    public int addSeason(Season season) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("AddSeason(?,?)");
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

    @Override
    public void loadSeasonById(int id, Season season) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB("LoadSeasonByName(?,?,?)");
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.executeLoad();


            season.setId(callDB.returnOutputParameterInt(2));
            season.setName(callDB.returnOutputParameterString(3));

        } catch (Exception e) {
            throw e;
        } finally {
            callDB.closeConnection();
        }

    }

}
