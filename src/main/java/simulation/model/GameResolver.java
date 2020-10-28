package simulation.model;

public class GameResolver extends ParentObj {
    private double randomWinChance;

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

    private Double randomWinChance;

    public Double getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(double randomWinChance) {
        this.randomWinChance = randomWinChance;
    }
}
