package dao;

import common.Constants;
import data.ILoadSeasonFactory;
import model.Season;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadSeasonDao implements ILoadSeasonFactory {

    @Override
    public void loadSeasonById(int id, Season season) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadSeasonByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);

            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                while (rs.next()) {
                    season = new Season();
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
