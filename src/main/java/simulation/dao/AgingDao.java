package simulation.dao;

import org.json.simple.JSONObject;
import simulation.model.IAging;

public class AgingDao extends DBExceptionLog implements IAgingDao {

    @Override
    public int addAging(IAging aging) throws Exception {
        return 0;
    }

    @Override
    public IAging loadAgingByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public void loadAgingById(int id, IAging aging) throws Exception {

    }

    @Override
    public void loadAgingFromJSON(IAging aging, JSONObject jsonObject){
        String AVERAGE_RETIREMENT_AGE = "averageRetirementAge";
        String MAXIMUM_AGE = "maximumAge";
        int averageRetirementAge = (int) (long) jsonObject.get(AVERAGE_RETIREMENT_AGE);
        int maximumAge = (int) (long) jsonObject.get(MAXIMUM_AGE);
        aging.setAverageRetirementAge(averageRetirementAge);
        aging.setMaximumAge(maximumAge);
    }
}
