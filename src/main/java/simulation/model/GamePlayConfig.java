package simulation.model;

import simulation.dao.IGamePlayConfigDao;
import simulation.dao.ITradingDao;

public class GamePlayConfig implements IGamePlayConfig {

    private int id;
    private int leagueId;
    private IAging aging;
    private IInjury injury;
    private ITrading trading;
    private ITraining training;
    private ISimulate simulate;

    public GamePlayConfig() {
        setId(System.identityHashCode(this));
    }

    public GamePlayConfig(int leagueId, IGamePlayConfigDao gamePlayConfigFactory) throws Exception {
        gamePlayConfigFactory.loadGamePlayConfigByLeagueId(leagueId, this);
    }

    public GamePlayConfig(simulation.serializers.ModelsForDeserialization.model.GamePlayConfig gamePlayConfig) {
        this.id = gamePlayConfig.id;
        this.aging = new Aging(gamePlayConfig.aging);
        this.leagueId = gamePlayConfig.leagueId;
        this.injury = new Injury(gamePlayConfig.injury);
        this.trading = new Trading(gamePlayConfig.trading);
        this.training = new Training(gamePlayConfig.training);
        this.simulate = new Simulate(gamePlayConfig.simulate);
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

    public IAging getAging() {
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

    public ISimulate getSimulate() {
        return simulate;
    }

    public void setSimulate(ISimulate simulate) {
        if (simulate == null) {
            return;
        }
        this.simulate = simulate;
    }
}
