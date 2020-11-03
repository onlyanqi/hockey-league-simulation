package db.data;

import simulation.model.League;

import java.util.List;

public interface ILeagueFactory {

    int addLeague(League league) throws Exception;

    void loadLeagueById(int id, League league) throws Exception;

    void loadLeagueByName(String leagueName, int userId, League league) throws Exception;

    List<League> loadLeagueListByUserId(int userId) throws Exception;
}
