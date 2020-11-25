package simulation.dao;

import org.json.simple.JSONObject;
import simulation.model.IConference;
import simulation.model.ILeague;

import java.util.List;

public interface ILeagueDao {

    int addLeague(ILeague league) throws Exception;

    void loadLeagueById(int id, ILeague league) throws Exception;

    void loadLeagueByName(String leagueName, int userId, ILeague league) throws Exception;

    List<ILeague> loadLeagueListByUserId(int userId) throws Exception;

    void loadLeagueFromJSON(ILeague league, JSONObject jsonObject);

    List<IConference> formConferenceList() throws Exception;
}
