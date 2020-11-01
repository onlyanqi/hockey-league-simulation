package simulation.mock;

import db.data.IAgingFactory;
import simulation.model.Aging;

public class AgingMock implements IAgingFactory {

    @Override
    public int addAging(Aging aging) {
        aging = new Aging(1);
        return aging.getId();
    }

    @Override
    public Aging loadAgingByLeagueId(int id) {

        Aging aging = new Aging();

        switch (id) {
            case 1:
                //all correct data
                aging.setAverageRetirementAge(35);
                aging.setMaximumAge(50);
                aging.setLeagueId(1);
                aging = new Aging(1);

            case 2:
                //max < average
                aging.setAverageRetirementAge(50);
                aging.setMaximumAge(30);
                aging.setLeagueId(2);
                aging = new Aging(2);

            case 3:
                //Average retirement age is below 0
                aging.setAverageRetirementAge(-3);
                aging.setMaximumAge(55);
                aging.setLeagueId(3);
                aging = new Aging(3);

            case 4:
                //Maximum retirement age is below 0
                aging.setAverageRetirementAge(30);
                aging.setMaximumAge(-55);
                aging.setLeagueId(3);
                aging = new Aging(4);
        }
        return aging;
    }

    @Override
    public void loadAgingById(int id, Aging aging) {
        switch (id) {
            case 1:
                //all correct data
                aging.setAverageRetirementAge(35);
                aging.setMaximumAge(50);
                aging.setLeagueId(1);
                break;

            case 2:
                //Average retirement age is below 0
                aging.setAverageRetirementAge(-3);
                aging.setMaximumAge(55);
                aging.setLeagueId(3);
                break;

            case 3:
                //Maximum age is below 0
                aging.setAverageRetirementAge(25);
                aging.setMaximumAge(-35);
                aging.setLeagueId(5);
                break;

            case 4:
                //max < average
                aging.setAverageRetirementAge(50);
                aging.setMaximumAge(30);
                aging.setLeagueId(2);
                break;

            case 5:
                //Average retirement age equals to max age
                aging.setAverageRetirementAge(100);
                aging.setMaximumAge(100);
                aging.setLeagueId(4);
                break;

        }
    }

}
