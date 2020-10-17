/*
package dao;

import common.Constants;
import simulation.data.ILoadSeasonFactory;
import simulation.model.Season;

public class LoadSeasonDao implements ILoadSeasonFactory {

    @Override
    public void loadSeasonById(int id, Season season) throws Exception {
        ICallDB callDB = null;
        try {
            callDB = new CallDB(Constants.loadSeasonByName);
            callDB.setInputParameterInt(1, id);
            callDB.setOutputParameterInt(2);
            callDB.setOutputParameterString(3);
            callDB.executeLoad();


            season.setId(callDB.returnOutputParameterInt(2));
            season.setName(callDB.returnOutputParameterString(3));

        }catch (Exception e){
            throw e;
        } finally {
            callDB.closeConnection();
        }

    }
}
*/
