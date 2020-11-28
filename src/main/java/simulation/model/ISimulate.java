package simulation.model;

public interface ISimulate {
    Double getUpset();

    void setUpset(Double upset);

    Double getDefendChance();

    void setDefendChance(Double defendChance);

    Double getPenaltyChance();

    void setPenaltyChance(Double penaltyChance);

    Double getGoalChance();

    void setGoalChance(Double goalChance);
}
