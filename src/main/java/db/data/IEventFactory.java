package db.data;

import simulation.model.NHLEvents;

public interface IEventFactory {
    long addEvent(int leagueId, NHLEvents event) throws Exception;

    void loadEventById(int id, NHLEvents event) throws Exception;

    void loadEventByLeagueId(int leagueId, NHLEvents event) throws Exception;
}
