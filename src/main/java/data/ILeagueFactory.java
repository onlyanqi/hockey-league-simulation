package data;

import model.League;

public interface ILeagueFactory {

    long addLeague(League league) throws Exception;
    void loadLeagueByName(long id, League league) throws Exception;

}
