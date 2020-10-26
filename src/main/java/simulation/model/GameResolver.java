package simulation.model;

public class GameResolver extends ParentObj{
    String name;

    public GameResolver() {
    }

    public GameResolver(int id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private double randomWinChance;

    public double getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(double randomWinChance) {
        this.randomWinChance = randomWinChance;
    }

}
