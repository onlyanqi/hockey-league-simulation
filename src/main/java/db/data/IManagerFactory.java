package db.data;

import simulation.model.Manager;

import java.util.List;

public interface IManagerFactory {

    int addManager(Manager manager) throws Exception;

    void loadManagerById(int managerId, Manager manager) throws Exception;

    List<Manager> loadFreeManagersByLeagueId(int leagueId) throws Exception;

    Manager loadManagerByTeamId(int teamId) throws Exception;

}