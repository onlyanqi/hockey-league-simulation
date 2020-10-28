package simulation.model;

import db.data.IAgingFactory;

public class AgingMock implements IAgingFactory {

    @Override
    public int addAging(Aging aging) throws Exception {
        aging = new Aging(1);
        return aging.getId();
    }

    @Override
    public Aging loadAgingByLeagueId(int id) throws Exception {
        Aging aging = new Aging();
        aging.setLeagueId(id);
        aging.setAverageRetirementAge(35);
        aging.setMaximumAge(50);
        return aging;
    }

    @Override
    public void loadAgingById(int id, Aging aging) {
        switch (id){
            case 1:
                //all correct data
                aging.setMaximumAge(50);
                aging.setAverageRetirementAge(35);
                break;

            case 2:
                //max < average
                aging.setMaximumAge(30);
                aging.setAverageRetirementAge(50);
                break;

            case 3:
                //Average retirement age is below 0
                aging.setMaximumAge(55);
                aging.setAverageRetirementAge(-3);
                break;
        }
    }

}
