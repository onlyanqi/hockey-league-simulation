package db.dao;

import db.data.ILeagueDao;
import org.json.simple.JSONObject;
import simulation.model.*;
import java.util.List;

public class LeagueDao extends DBExceptionLog implements ILeagueDao {

    @Override
    public int addLeague(ILeague league) throws Exception {
        return 0;
    }

    @Override
    public void loadLeagueById(int id, ILeague league) throws Exception {

    }

    @Override
    public void loadLeagueByName(String leagueName, int userId, ILeague league) throws Exception {

    }

    @Override
    public List<ILeague> loadLeagueListByUserId(int userId) throws Exception {
        return null;
    }

    @Override
    public void loadLeagueFromJSON(ILeague league, JSONObject jsonObject) {

    }
}
