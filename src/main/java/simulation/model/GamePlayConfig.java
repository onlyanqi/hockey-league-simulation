package simulation.model;

import db.data.IGamePlayConfigDao;
import db.data.ITradingDao;

public class GamePlayConfig implements IGamePlayConfig{

    private int id;
    private int leagueId;
    private IAging aging;
    private IInjury injury;
    private ITrading trading;
    private ITraining training;

    public GamePlayConfig() {
        setId(System.identityHashCode(this));
    }

    public GamePlayConfig(int leagueId, IGamePlayConfigDao gamePlayConfigFactory) throws Exception {
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

    public IAging getAging(){
        return aging;
    }

    public void setAging(IAging aging) {
        if (aging == null) {
            return;
        }
        this.aging = (Aging) aging;
    }

    public IInjury getInjury() {
        return injury;
    }

    public void setInjury(IInjury injury) {
        if (injury == null) {
            return;
        }
        this.injury = injury;
    }

    public ITrading getTrading() {
        return trading;
    }

    public void setTrading(ITrading trading) {
        if (trading == null) {
            return;
        }
        this.trading = trading;
    }

    public void loadTradingDetailsByLeagueId(ITradingDao tradingFactory) throws Exception {
        this.trading = tradingFactory.loadTradingDetailsByLeagueId(getId());
    }

    public ITraining getTraining() {
        return training;
    }

    public void setTraining(ITraining training) {
        if (training == null) {
            return;
        }
        this.training = training;
    }
}
