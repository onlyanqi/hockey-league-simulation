package simulation.dao;

import simulation.model.ISeason;

public class SeasonDao extends DBExceptionLog implements ISeasonDao {


    @Override
    public int addSeason(ISeason season) throws Exception {
        return 0;
    }

    @Override
    public void loadSeasonById(int id, ISeason season) throws Exception {

    }
}
