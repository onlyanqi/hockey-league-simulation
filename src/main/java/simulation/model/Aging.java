package simulation.model;


public class Aging extends GamePlayConfig {
    private int averageRetirementAge;

//    public Aging(int id) {
//        setId(id);
//    }

//    public Aging (int id, IAgingFactory factory) throws Exception {
//        setId(id);
//        factory.loadAgingByLeagueId(id, this);
//    }
    private int maximumAge;

    public Aging() {
    }

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
