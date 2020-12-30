package persistance.dao;

import simulation.model.ISeason;

public interface ISeasonDao {

    int addSeason(ISeason season) throws Exception;

    void loadSeasonById(int id, ISeason season) throws Exception;
}
