package db.data;

import simulation.model.IManager;
import simulation.model.Manager;

import java.util.List;

public interface IManagerDao {

    int addManager(IManager manager) throws Exception;

    void loadManagerById(int managerId, IManager manager) throws Exception;

    List<IManager> loadFreeManagersByLeagueId(int leagueId) throws Exception;

    IManager loadManagerByTeamId(int teamId) throws Exception;

}