package simulation.model;

import persistance.dao.ISeasonDao;

public interface ISeason {

    void addSeason(ISeasonDao addSeasonFactory) throws Exception;

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);
}
