package simulation.model;

import db.data.IAgingFactory;

public class Aging extends SharedAttributes {

    private int averageRetirementAge;

    private int maximumAge;

    private int leagueId;

    public Aging() {
    }

    public Aging(int id) {
        setId(id);
    }

    public Aging(int id, IAgingFactory loadAgingFactory) throws Exception {
        setId(id);
        loadAgingFactory.loadAgingById(id, this);
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getAverageRetirementAge() {
        return averageRetirementAge;
    }

    public void setAverageRetirementAge(int averageRetirementAge) throws IllegalArgumentException {
        if (averageRetirementAge < 0) {
            throw new IllegalArgumentException("averageRetirementAge must be greater than 0!");
        }
        this.averageRetirementAge = averageRetirementAge;
    }

    public int getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(int maximumAge) throws IllegalArgumentException {
        if (maximumAge < 0) {
            throw new IllegalArgumentException("maximumAge must be greater than 0!");
        }
        if (this.getAverageRetirementAge() >= maximumAge) {
            throw new IllegalArgumentException("Maximum retirement age must be greater than average retirement age!");
        }
        this.maximumAge = maximumAge;
    }
}
