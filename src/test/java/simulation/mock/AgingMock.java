package simulation.mock;

import org.json.simple.JSONObject;
import simulation.dao.IAgingDao;
import simulation.model.Aging;
import simulation.model.IAging;

public class AgingMock implements IAgingDao {

    @Override
    public int addAging(IAging aging) {
        aging = new Aging(1);
        return aging.getId();
    }

    @Override
    public IAging loadAgingByLeagueId(int id) {

        Aging aging = new Aging();

        switch (id) {
            case 1:
                aging.setAverageRetirementAge(35);
                aging.setMaximumAge(50);
                aging.setLeagueId(1);
                aging = new Aging(1);

            case 2:
                aging.setAverageRetirementAge(50);
                aging.setMaximumAge(30);
                aging.setLeagueId(2);
                aging = new Aging(2);

            case 3:
                aging.setAverageRetirementAge(-3);
                aging.setMaximumAge(55);
                aging.setLeagueId(3);
                aging = new Aging(3);

            case 4:
                aging.setAverageRetirementAge(30);
                aging.setMaximumAge(-55);
                aging.setLeagueId(3);
                aging = new Aging(4);
        }
        return aging;
    }

    @Override
    public void loadAgingById(int id, IAging aging) {
        switch (id) {
            case 1:
                aging.setAverageRetirementAge(35);
                aging.setMaximumAge(50);
                aging.setLeagueId(1);
                aging.setStatDecayChance(1.0);
                break;

            case 2:
                aging.setAverageRetirementAge(-3);
                aging.setMaximumAge(55);
                aging.setLeagueId(3);
                aging.setStatDecayChance(0.02);
                break;

            case 3:
                aging.setAverageRetirementAge(25);
                aging.setMaximumAge(-35);
                aging.setLeagueId(5);
                aging.setStatDecayChance(0.01);
                break;

            case 4:
                aging.setAverageRetirementAge(50);
                aging.setMaximumAge(30);
                aging.setLeagueId(2);
                aging.setStatDecayChance(0.01);
                break;

            case 5:
                aging.setAverageRetirementAge(100);
                aging.setMaximumAge(100);
                aging.setLeagueId(4);
                aging.setStatDecayChance(0.01);
                break;

        }
    }

    @Override
    public void loadAgingFromJSON(IAging aging, JSONObject jsonObject) {

    }

}
