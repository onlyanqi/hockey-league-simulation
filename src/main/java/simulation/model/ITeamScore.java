package simulation.model;

public interface ITeamScore {

    String getTeamName();

    void setTeamName(String teamName);

    Integer getPoints();

    void setPoints(Integer points);

    Integer getNumberOfWins();

    void setNumberOfWins(Integer numberOfWins);

    Integer getNumberOfLoss();

    void setNumberOfLoss(Integer numberOfLoss);

    Integer getNumberOfTies();

    void setNumberOfTies(Integer numberOfTies);

    void setId(int id);

    int getId();

    String getName();
}
