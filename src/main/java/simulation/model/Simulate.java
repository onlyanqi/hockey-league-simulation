package simulation.model;

public class Simulate extends SharedAttributes implements ISimulate {
    private Double upset;
    private Double defendChance;
    private Double penaltyChance;
    private Double goalChance;

    public Simulate() {
        setId(System.identityHashCode(this));
    }

    public Simulate(int id) {
        setId(id);
    }

    public Simulate(simulation.serializers.ModelsForDeserialization.model.Simulate simulate){
        this.upset = simulate.upset;
        this.defendChance = simulate.defendChance;
        this.penaltyChance = simulate.penaltyChance;
        this.goalChance = simulate.goalChance;
        this.setName(simulate.name);
        this.setId(simulate.id);
    }

    @Override
    public Double getUpset() {
        return upset;
    }

    @Override
    public void setUpset(Double upset) {
        this.upset = upset;
    }

    @Override
    public Double getDefendChance() {
        return defendChance;
    }

    @Override
    public void setDefendChance(Double defendChance) {
        this.defendChance = defendChance;
    }

    @Override
    public Double getPenaltyChance() {
        return penaltyChance;
    }

    @Override
    public void setPenaltyChance(Double penaltyChance) {
        this.penaltyChance = penaltyChance;
    }

    @Override
    public Double getGoalChance() {
        return goalChance;
    }

    @Override
    public void setGoalChance(Double goalChance) {
        this.goalChance = goalChance;
    }
}
