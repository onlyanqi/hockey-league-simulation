package simulation.mock;

import db.data.IInjuryFactory;
import simulation.model.Injury;

public class InjuryMock implements IInjuryFactory {
    @Override
    public int addInjury(Injury injury) throws Exception {
        injury = new Injury(1);
        return injury.getId();
    }

    @Override
    public Injury loadInjuryByLeagueId(int leagueId) throws Exception {
        Injury injury = new Injury();
        switch (leagueId) {
            case 1:
                injury.setRandomInjuryChance(0.05);
                injury.setInjuryDaysHigh(200);
                injury.setInjuryDaysLow(20);
                injury.setLeagueId(leagueId);
                break;
            case 2:
                injury.setRandomInjuryChance(0.01);
                injury.setInjuryDaysHigh(188);
                injury.setInjuryDaysLow(9);
                injury.setLeagueId(leagueId);
                break;
            case 3:
                injury.setRandomInjuryChance(0.01);
                injury.setInjuryDaysHigh(10);
                injury.setInjuryDaysLow(200);
                injury.setLeagueId(leagueId);
                break;
        }
        return injury;
    }

    @Override
    public void loadInjuryById(int id, Injury injury) throws Exception {
        switch (id) {
            case 1:
                injury.setId(id);
                injury.setRandomInjuryChance(1.00);
                injury.setInjuryDaysLow(20);
                injury.setInjuryDaysHigh(200);
                injury.setLeagueId(1);
                break;
            case 2:
                injury.setId(id);
                injury.setRandomInjuryChance(1.22);
                injury.setInjuryDaysLow(9);
                injury.setInjuryDaysHigh(188);
                injury.setLeagueId(2);
                break;
            case 3:
                injury.setId(id);
                injury.setRandomInjuryChance(0.01);
                injury.setInjuryDaysLow(-9);
                injury.setInjuryDaysHigh(188);
                injury.setLeagueId(3);
                break;
            case 4:
                injury.setId(id);
                injury.setRandomInjuryChance(0.01);
                injury.setInjuryDaysLow(9);
                injury.setInjuryDaysHigh(-188);
                injury.setLeagueId(4);
                break;
            case 5:
                injury.setRandomInjuryChance(0.01);
                injury.setInjuryDaysLow(200);
                injury.setInjuryDaysHigh(10);
                injury.setLeagueId(5);
                break;
            case 6:
                injury.setId(id);
                injury.setRandomInjuryChance(0.05);
                injury.setInjuryDaysLow(20);
                injury.setInjuryDaysHigh(200);
                injury.setLeagueId(1);
                break;
        }
    }
}
