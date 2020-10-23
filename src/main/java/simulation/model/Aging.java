package simulation.model;


public class Aging extends GamePlayConfig{
    public Aging() {
    }

//    public Aging(int id) {
//        setId(id);
//    }

//    public Aging (int id, IAgingFactory factory) throws Exception {
//        setId(id);
//        factory.loadAgingByLeagueId(id, this);
//    }

    private long averageRetirementAge;

    private long maximumAge;

    public long getAverageRetirementAge() {
        return averageRetirementAge;
    }

    public void setAverageRetirementAge(long averageRetirementAge) {
        this.averageRetirementAge = averageRetirementAge;
    }

    public long getMaximumAge() {
        return maximumAge;
    }

    public void setMaximumAge(long maximumAge) {
        this.maximumAge = maximumAge;
    }
}
