package simulation.model;

import com.google.gson.annotations.SerializedName;
import db.data.ITradingFactory;

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
        this.aging = aging;
    }

    public Injury getInjury() {
        return injury;
    }

    public void setInjury(Injury injury) {
        this.injury = injury;
    }

    public GameResolver getGameResolver() {
        return gameResolver;
    }

    public void setGameResolver(GameResolver gameResolver) {
        this.gameResolver = gameResolver;
    }

    public Trading getTrading() {
        return trading;
    }

    public void setTrading(Trading trading) {
        this.trading = trading;
    }

    public void loadTradingDetailsByLeagueId(ITradingFactory tradingFactory) throws Exception {
        this.trading = tradingFactory.loadTradingDetailsByLeagueId(getId());
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
