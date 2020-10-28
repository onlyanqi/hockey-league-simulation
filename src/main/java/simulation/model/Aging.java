package simulation.model;


import db.data.IAgingFactory;

public class Aging extends ParentObj {
    public Aging() {
    }

    public Aging(int id) {
        setId(id);
    }

    public Aging(int id, IAgingFactory loadAgingFactory) throws Exception {
        setId(id);
        loadAgingFactory.loadAgingById(id, this);
    }

    private int averageRetirementAge;

    private int maximumAge;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    private int leagueId;

    public int getAverageRetirementAge() {
        return averageRetirementAge;
    }

    public void setAverageRetirementAge(int averageRetirementAge) {
        this.averageRetirementAge = averageRetirementAge;
    }

    public int getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(int maximumAge) {
        this.maximumAge = maximumAge;
    }
}
