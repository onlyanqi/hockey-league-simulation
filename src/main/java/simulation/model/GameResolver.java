package simulation.model;

public class GameResolver implements ParentObj{
    private Double randomWinChance;

    public GameResolver() {
    }

    public GameResolver(int id) {
        setId(id);
    }


    public double getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(double randomWinChance) {
        this.randomWinChance = randomWinChance;
    }
}
