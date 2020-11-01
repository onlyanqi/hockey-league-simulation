package simulation.model;

import db.data.ITeamScoreFactory;

public class TeamScore extends SharedAttributes{

    public TeamScore(){}

    public TeamScore(String teamName){
        this.teamName = teamName;
        this.numberOfLoss = 0;
        this.numberOfWins = 0;
        this.numberOfTies = 0;
        this.points = 0;
    }

    public TeamScore(int id, ITeamScoreFactory iTeamScoreFactory) throws Exception{
        iTeamScoreFactory.loadTeamScoreById(id,this);
    }

    String teamName;
    Integer points;
    Integer numberOfWins;
    Integer numberOfLoss;
    Integer numberOfTies;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(Integer numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public Integer getNumberOfLoss() {
        return numberOfLoss;
    }

    public void setNumberOfLoss(Integer numberOfLoss) {
        this.numberOfLoss = numberOfLoss;
    }

    public Integer getNumberOfTies() {
        return numberOfTies;
    }

    public void setNumberOfTies(Integer numberOfTies) {
        this.numberOfTies = numberOfTies;
    }
}
