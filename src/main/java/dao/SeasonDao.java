package dao;

import common.Constants;
import data.ISeasonFactory;
import model.Season;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonDao implements ISeasonFactory {
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

    @Override
    public void loadSeasonByName(int id, Season season) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadSeasonByName);
            callDB.setInputParameterString(1, season.getName());
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);

            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    season.setId(rs.getInt(2));
                    season.setName(rs.getString(3));
                }
            }
        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }

    }
}
