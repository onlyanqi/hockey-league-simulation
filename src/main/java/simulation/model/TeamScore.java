package simulation.model;

import simulation.dao.ITeamScoreDao;

public class TeamScore extends SharedAttributes implements ITeamScore{

    ITeam team;
    String teamName;
    Integer points;
    Integer numberOfWins;
    Integer numberOfLoss;
    Integer numberOfTies;

    public TeamScore() {
        setId(System.identityHashCode(this));
    }

    public TeamScore(ITeam team) {
        this.team = team;
        this.teamName = team.getName();
        this.numberOfLoss = 0;
        this.numberOfWins = 0;
        this.numberOfTies = 0;
        this.points = 0;
    }

    public TeamScore(int id, ITeamScoreDao iTeamScoreDao) throws Exception {
        iTeamScoreDao.loadTeamScoreById(id, this);
    }

    public TeamScore(simulation.serializers.ModelsForDeserialization.model.TeamScore teamScore){
        this.teamName = teamScore.teamName;
        this.points = teamScore.points;
        this.numberOfLoss = teamScore.numberOfLoss;
        this.numberOfTies = teamScore.numberOfTies;
        this.numberOfWins = teamScore.numberOfWins;
        this.setId(teamScore.id);
        this.setName(teamScore.name);
    }

    public ITeam getTeam() {
        return team;
    }

    public void setTeam(ITeam team) {
        this.team = team;
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

    @Override
    public void setTeamName(String teamName){
        this.teamName = teamName;
    }
}
