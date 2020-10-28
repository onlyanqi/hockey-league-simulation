package simulation.model;

public class GameResolver extends SharedAttributes {
    public GameResolver() {
    }

    public GameResolver(int id) {
        setId(id);
    }

    private double randomWinChance;

    public double getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(double randomWinChance) {
        this.randomWinChance = randomWinChance;
    }

}
