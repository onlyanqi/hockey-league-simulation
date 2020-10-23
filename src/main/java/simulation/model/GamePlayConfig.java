package simulation.model;


public class GamePlayConfig {
    private int leagueId;
    private Aging aging;
    private Injury injury;
    private GameResolver gameResolver;
    private Trading trading;
    private Training training;

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public void setAging(Aging aging) {
        this.aging = aging;
    }

    public Aging getAging() {
        return aging;
    }

    public void setInjury(Injury injury) {
        this.injury = injury;
    }

    public Injury getInjury() {
        return injury;
    }

    public void setGameResolver(GameResolver gameResolver) {
        this.gameResolver = gameResolver;
    }

    public GameResolver getGameResolver() {
        return gameResolver;
    }

    public void setTrading( Trading trading){
        this.trading = trading;
    }

    public Trading getTrading(){
        return trading;
    }

    public void setTraining(Training training){
        this.training = training;
    }

    public Training getTraining(){
        return training;
    }
}
