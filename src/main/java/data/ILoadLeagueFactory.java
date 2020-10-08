package data;

import model.League;

public interface ILoadLeagueFactory {

    void loadLeagueById(int id, League league) throws Exception;
    League loadLeagueByName(String leagueName) throws Exception;

}
