package simulation.mock;

import db.data.IAgingFactory;
import simulation.model.Aging;

public class AgingMock implements IAgingFactory {

    @Override
    public int addAging(Aging aging) throws Exception {
        aging = new Aging(1);
        return aging.getId();
    }

    @Override
    public Aging loadAgingByLeagueId(int id) throws Exception {

        Aging aging = new Aging();

        switch (id) {
            case 1:
                //all correct data
                aging.setMaximumAge(50);
                aging.setAverageRetirementAge(35);
                aging.setLeagueId(1);
                aging = new Aging(1);

            case 2:
                //max < average
                aging.setMaximumAge(30);
                aging.setAverageRetirementAge(50);
                aging.setLeagueId(2);
                aging = new Aging(2);

            case 3:
                //Average retirement age is below 0
                aging.setMaximumAge(55);
                aging.setAverageRetirementAge(-3);
                aging.setLeagueId(3);
                aging = new Aging(3);
        }
        return aging;
    }

    @Override
    public void loadAgingById(int id, Aging aging) throws Exception {
        switch (id) {
            case 1:
                //all correct data
                aging.setMaximumAge(50);
                aging.setAverageRetirementAge(35);
                aging.setLeagueId(1);
                break;

            case 2:
                //max < average
                aging.setMaximumAge(30);
                aging.setAverageRetirementAge(50);
                aging.setLeagueId(2);
                break;

            case 3:
                //Average retirement age is below 0
                aging.setMaximumAge(55);
                aging.setAverageRetirementAge(-3);
                aging.setLeagueId(3);
                break;

            case 4:
                //Average retirement age equals to max age
                aging.setMaximumAge(100);
                aging.setAverageRetirementAge(100);
                aging.setLeagueId(4);
                break;

            case 5:
                //Maximum age is below 0
                aging.setMaximumAge(-35);
                aging.setAverageRetirementAge(25);
                aging.setLeagueId(5);
                break;
        }
    }

}
