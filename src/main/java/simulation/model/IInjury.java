package simulation.model;

public interface IInjury {

    Double getRandomInjuryChance();

    void setRandomInjuryChance(Double randomInjuryChance);

    int getInjuryDaysLow();

    void setInjuryDaysLow(int injuryDaysLow);

    int getInjuryDaysHigh();

    void setInjuryDaysHigh(int injuryDaysHigh);

    int getLeagueId();

    void setLeagueId(int leagueId);

    int getId();

    void setId(int id);

}
