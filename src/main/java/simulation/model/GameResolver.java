package simulation.model;

public class GameResolver extends ParentObj{
    public GameResolver() {
    }

    public GameResolver(int id) {
        setId(id);
    }

    private float randomWinChance;

    public float getRandomWinChance() {
        return randomWinChance;
    }

    public void setRandomWinChance(float randomWinChance) {
        this.randomWinChance = randomWinChance;
    }

}
