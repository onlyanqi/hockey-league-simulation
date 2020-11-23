package db.dao;

import db.data.IEventDao;
import simulation.model.INHLEvents;

public class EventDao extends DBExceptionLog implements IEventDao {

    @Override
    public long addEvent(int leagueId, INHLEvents event) throws Exception {
        return 0;
    }

    @Override
    public void loadEventById(int id, INHLEvents nhlEvents) throws Exception {

    }

    @Override
    public void loadEventByLeagueId(int leagueId, INHLEvents event) throws Exception {

    }

}
