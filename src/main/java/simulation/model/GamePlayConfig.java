package simulation.model;

import com.google.gson.annotations.SerializedName;

public class GamePlayConfig extends SharedAttributes{

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

    public Aging getAging() {
        return aging;
    }

    public void setAging(Aging aging) {
        if(aging == null){
            return;
        }
        this.aging = aging;
    }

    public Injury getInjury() {
        return injury;
    }

    public void setInjury(Injury injury) {
        if(injury == null){
            return;
        }
        this.injury = injury;
    }

    public GameResolver getGameResolver() {
        return gameResolver;
    }

    public void setGameResolver(GameResolver gameResolver) {
        if(gameResolver == null){
            return;
        }
        this.gameResolver = gameResolver;
    }

    public Trading getTrading() {
        return trading;
    }

    public void setTrading(Trading trading) {
        if(trading == null){
            return;
        }
        this.trading = trading;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        if(training == null){
            return;
        }
        this.training = training;
    }
}
