package simulation.model;

public interface ITeamScore {

    ITeam getTeam();

    void setTeam(ITeam team);

    Integer getPoints();

    void setPoints(Integer points);

    Integer getNumberOfWins();

    void setNumberOfWins(Integer numberOfWins);

    Integer getNumberOfLoss();

    void setNumberOfLoss(Integer numberOfLoss);

    Integer getNumberOfTies();

    void setNumberOfTies(Integer numberOfTies);

    int getId();

    void setId(int id);

    String getName();

    void setTeamName(String teamName);
}
