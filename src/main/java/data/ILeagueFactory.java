package data;

import model.League;

public interface ILeagueFactory {

    int addLeague(League league) throws Exception;
    void loadLeagueByName(int id, League league) throws Exception;

}
