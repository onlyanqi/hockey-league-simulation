package db.data;

import org.json.simple.JSONObject;
import simulation.model.IAging;

public interface IAgingDao {
    int addAging(IAging aging) throws Exception;

    IAging loadAgingByLeagueId(int leagueId) throws Exception;

    void loadAgingById(int id, IAging aging) throws Exception;

    void loadAgingFromJSON(IAging aging, JSONObject jsonObject);
}
