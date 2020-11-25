package simulation.model;

import db.data.ITradingDao;

public interface IGamePlayConfig {

    int getId();

    void setId(int id);

    int getLeagueId();

    void setLeagueId(int leagueId);

    IAging getAging();

    void setAging(IAging aging);

    IInjury getInjury();

    void setInjury(IInjury injury);

    ITrading getTrading();

    void setTrading(ITrading trading);

    void loadTradingDetailsByLeagueId(ITradingDao tradingFactory) throws Exception;

    ITraining getTraining();

    void setTraining(ITraining training);
}
