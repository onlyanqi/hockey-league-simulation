package simulation.model;

public interface IGameResolver {

    Double getRandomWinChance();

    void setRandomWinChance(double randomWinChance);

    int getId();

    void setId(int id);
}
