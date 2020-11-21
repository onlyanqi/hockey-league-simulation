package db.dao;

import db.data.IManagerDao;
import simulation.model.IManager;

import java.util.List;

public class ManagerDao extends DBExceptionLog implements IManagerDao {


    @Override
    public int addManager(IManager manager) throws Exception {
        return 0;
    }

    @Override
    public void loadManagerById(int managerId, IManager manager) throws Exception {

    }

    @Override
    public List<IManager> loadFreeManagersByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public IManager loadManagerByTeamId(int teamId) throws Exception {
        return null;
    }
}
