package simulation.model;

import java.util.HashMap;

public class TeamStat {

    public String teamName;
    private int goals;
    private int saves;
    private int shots;
    private int penalties;
    private int numberOfGamesPlayed;

    public TeamStat(){}

    public TeamStat(simulation.serializers.ModelsForDeserialization.model.TeamStat teamStat){
        this.teamName = teamStat.teamName;
        this.goals = teamStat.goals;
        this.saves = teamStat.saves;
        this.shots = teamStat.shots;
        this.penalties = teamStat.penalties;
        this.numberOfGamesPlayed = teamStat.penalties;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public Integer getSaves() {
        return saves;
    }

    public void setSaves(Integer saves) {
        this.saves = saves;
    }

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public Integer getPenalties() {
        return penalties;
    }

    public void setPenalties(Integer penalties) {
        this.penalties = penalties;
    }

    public Integer getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public void setNumberOfGamesPlayed(Integer numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }
}
