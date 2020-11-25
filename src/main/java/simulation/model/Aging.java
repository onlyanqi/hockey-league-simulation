package simulation.model;

import simulation.dao.IAgingDao;

public class Aging extends SharedAttributes implements IAging {

    private int averageRetirementAge;

    private int maximumAge;

    private int leagueId;

    private Double statDecayChance;

    public Aging() {
        setId(System.identityHashCode(this));
    }

    public Aging(int id) {
        setId(id);
    }

    public Aging(int id, IAgingDao loadAgingFactory) throws Exception {
        setId(id);
        loadAgingFactory.loadAgingById(id, this);
    }

    @Override
    public int getLeagueId() {
        return leagueId;
    }

    @Override
    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public int getAverageRetirementAge() {
        return averageRetirementAge;
    }

    @Override
    public void setAverageRetirementAge(int averageRetirementAge) throws IllegalArgumentException {
        if (averageRetirementAge < 0) {
            throw new IllegalArgumentException("averageRetirementAge must be greater than 0!");
        }
        this.averageRetirementAge = averageRetirementAge;
    }

    @Override
    public int getMaximumAge() {
        return maximumAge;
    }

    @Override
    public void setMaximumAge(int maximumAge) throws IllegalArgumentException {
        if (maximumAge < 0) {
            throw new IllegalArgumentException("maximumAge must be greater than 0!");
        }
        if (this.getAverageRetirementAge() >= maximumAge) {
            throw new IllegalArgumentException("Maximum retirement age must be greater than average retirement age!");
        }
        this.maximumAge = maximumAge;
    }

    @Override
    public Double getStatDecayChance() {
        return statDecayChance;
    }

    @Override
    public void setStatDecayChance(Double statDecayChance) {
        this.statDecayChance = statDecayChance;
    }
}
