package simulation.model;

public class GameResolver extends SharedAttributes {
public class GameResolver extends ParentObj {
    private double randomWinChance;

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
