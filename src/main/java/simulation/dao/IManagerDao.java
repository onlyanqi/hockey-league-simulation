package simulation.dao;

import simulation.model.IManager;

import java.util.List;

public interface IManagerDao {

    int addManager(IManager manager) throws Exception;

    void loadManagerById(int managerId, IManager manager) throws Exception;

    List<IManager> loadFreeManagersByLeagueId(int leagueId) throws Exception;

    IManager loadManagerByTeamId(int teamId) throws Exception;

}