package simulation.model;

public class Injury {
    public Injury() {
    }

    private double randomInjuryChance;

    private long injuryDaysLow;

    private long injuryDaysHigh;

    public double getRandomInjuryChance() {
        return randomInjuryChance;
    }

    public void setRandomInjuryChance(double randomInjuryChance) {
        this.randomInjuryChance = randomInjuryChance;
    }

    public long getInjuryDaysLow() {
        return injuryDaysLow;
    }

    public void setInjuryDaysLow(long injuryDaysLow) {
        this.injuryDaysLow = injuryDaysLow;
    }

    public long getInjuryDaysHigh() {
        return injuryDaysHigh;
    }

    public void setInjuryDaysHigh(long injuryDaysHigh) {
        this.injuryDaysHigh = injuryDaysHigh;
    }


}
