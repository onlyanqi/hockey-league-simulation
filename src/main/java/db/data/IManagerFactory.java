package db.data;

import simulation.model.Manager;

public interface IManagerFactory {

    int addManager(Manager manager) throws Exception;

    void loadManagerById(int id, Manager manager) throws Exception;

    Manager loadManagerByLeagueId(int id) throws Exception;
}