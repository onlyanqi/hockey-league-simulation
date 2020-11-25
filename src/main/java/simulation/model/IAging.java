package simulation.model;

import java.time.LocalDate;

public interface IAging {

    int getLeagueId();

    void setLeagueId(int leagueId);

    int getAverageRetirementAge();

    void setAverageRetirementAge(int averageRetirementAge) throws IllegalArgumentException;

    int getMaximumAge();

    void setMaximumAge(int maximumAge) throws IllegalArgumentException;

    int getId();

    Double getStatDecayChance();

    void setStatDecayChance(Double statDecayChance);

    void agingPlayerDay(ILeague league);

    void agingPlayerPeriod(ILeague league, LocalDate before);

    void agingPlayerRetirement(ILeague league);

}
