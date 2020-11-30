package persistance.dao;

import simulation.model.INHLEvents;

public interface IEventDao {

    long addEvent(int leagueId, INHLEvents event) throws Exception;

    void loadEventById(int id, INHLEvents event) throws Exception;

    void loadEventByLeagueId(int leagueId, INHLEvents event) throws Exception;
}
