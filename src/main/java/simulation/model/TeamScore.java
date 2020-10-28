package simulation.model;

public class TeamScore{

    String teamName;
    Integer points;
    Integer numberOfWins;
    Integer numberOfLoss;
    Integer numberOfTies;

    public TeamScore(String teamName){
        this.teamName = teamName;
        this.numberOfLoss = 0;
        this.numberOfWins = 0;
        this.numberOfTies = 0;
        this.points = 0;
    }

    public TeamScore(String teamName, Integer score, Integer numberOfWins, Integer numberOfLoss, Integer numberOfTies) {
        this.teamName = teamName;
        this.points = score;
        this.numberOfWins = numberOfWins;
        this.numberOfLoss = numberOfLoss;
        this.numberOfTies = numberOfTies;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getScore() {
        return points;
    }

    public void setScore(Integer score) {
        this.points = score;
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
