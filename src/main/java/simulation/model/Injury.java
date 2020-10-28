package simulation.model;

public class Injury {
    public Injury() {
    }

    private Double randomInjuryChance;

    private int injuryDaysLow;

    private int injuryDaysHigh;

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


}
