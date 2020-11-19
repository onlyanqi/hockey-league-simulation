package simulation.model;

import db.data.IGamePlayConfigFactory;
import db.data.ITradingFactory;

public class GamePlayConfig {

    private int id;
    private int leagueId;
    //private IAging aging;
    private Aging aging;
    private Injury injury;
    private GameResolver gameResolver;
    private Trading trading;
    private Training training;

    public GamePlayConfig() {
        setId(System.identityHashCode(this));
    }

    public GamePlayConfig(int leagueId, IGamePlayConfigFactory gamePlayConfigFactory) throws Exception {
        gamePlayConfigFactory.loadGamePlayConfigByLeagueId(leagueId, this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    //public IAging getAging() {
    public Aging getAging(){
        return aging;
    }

    //public void setAging(IAging aging) {
    public void setAging(IAging aging) {
        if (aging == null) {
            return;
        }
        this.aging = (Aging) aging;
    }

    public Injury getInjury() {
        return injury;
    }

    public void setInjury(Injury injury) {
        if (injury == null) {
            return;
        }
        this.injury = injury;
    }

    public GameResolver getGameResolver() {
        return gameResolver;
    }

    public void setGameResolver(GameResolver gameResolver) {
        if (gameResolver == null) {
            return;
        }
        this.gameResolver = gameResolver;
    }

    public Trading getTrading() {
        return trading;
    }

    public void setTrading(Trading trading) {
        if (trading == null) {
            return;
        }
        this.trading = trading;
    }

    public void loadTradingDetailsByLeagueId(ITradingFactory tradingFactory) throws Exception {
        this.trading = tradingFactory.loadTradingDetailsByLeagueId(getId());
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        if (training == null) {
            return;
        }
        this.training = training;
    }
}
