package simulation.model;

import db.data.IAgingFactory;
import db.data.IInjuryFactory;

public class Injury extends ParentObj{

    private Double randomInjuryChance;

    private int injuryDaysLow;

    private int injuryDaysHigh;

    private int leagueId;

    public Injury(){
    }

    public Injury(int id){
        setId(id);
    }

    public Injury(int id, IInjuryFactory loadInjuryFactory) throws Exception {
        setId(id);
        loadInjuryFactory.loadInjuryById(id, this);
    }

    public Double getRandomInjuryChance() {
        return randomInjuryChance;
    }

    public void setRandomInjuryChance(Double randomInjuryChance) {
        this.randomInjuryChance = randomInjuryChance;
    }

    public int getInjuryDaysLow() {
        return injuryDaysLow;
    }

    public void setInjuryDaysLow(int injuryDaysLow) {
        this.injuryDaysLow = injuryDaysLow;
    }

    public int getInjuryDaysHigh() {
        return injuryDaysHigh;
    }

    public void setInjuryDaysHigh(int injuryDaysHigh) {
        this.injuryDaysHigh = injuryDaysHigh;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

}
