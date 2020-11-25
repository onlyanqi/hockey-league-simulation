package simulation.serializers.ModelsForDeserialization.model;

import simulation.model.Position;

import java.time.LocalDate;

public class Player extends SharedAttributes {

    public int age;
    public LocalDate birthday;
    public Position position;
    public int teamId;
    public int freeAgentId;
    public boolean isCaptain;
    public boolean isInjured;
    public LocalDate injuryStartDate;
    public int injuryDatesRange;
    public boolean isFreeAgent = false;
    public boolean isRetired = false;
    public int skating;
    public int shooting;
    public int checking;
    public int saving;
    public double strength;
    public double relativeStrength;
    public int penaltyCount;
    public int goalScore;

    public int saves;
}
