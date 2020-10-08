package data;

import model.League;
import java.util.List;

public interface ILoadLeagueFactory {

    void loadLeagueById(int id, League league) throws Exception;
    League loadLeagueByName(String leagueName) throws Exception;
    List<League> loadLeagueListByUserId(int userId) throws Exception;
}
