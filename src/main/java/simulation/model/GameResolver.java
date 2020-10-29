package simulation.model;

public class GameResolver extends SharedAttributes{
    private Double randomWinChance;

    public GameResolver() {
    }

    public GameResolver(int id) {
        setId(id);
    }


    public Double getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(double randomWinChance) {
        this.randomWinChance = randomWinChance;
    }
}
