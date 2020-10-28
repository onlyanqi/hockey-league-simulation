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

    private Double randomWinChance;

    public Double getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(Double randomWinChance) {
        this.randomWinChance = randomWinChance;
    }

}
