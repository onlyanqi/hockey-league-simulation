package simulation.model;

public class Injury extends ParentObj{
    public Injury() {
    }

    public Injury(int id) {
        setId(id);
    }

    private float randomInjuryChance;

    private int injuryDaysLow;

    private int injuryDaysHigh;

    public float getRandomInjuryChance() {
        return randomInjuryChance;
    }

    public void setRandomInjuryChance(float randomInjuryChance) {
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


}
