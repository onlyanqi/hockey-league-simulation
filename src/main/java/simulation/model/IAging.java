package simulation.model;

public interface IAging {

    int getLeagueId();

    void setLeagueId(int leagueId);

    int getAverageRetirementAge();

    void setAverageRetirementAge(int averageRetirementAge) throws IllegalArgumentException;

    int getMaximumAge();

    void setMaximumAge(int maximumAge) throws IllegalArgumentException;

    int getId();
}
