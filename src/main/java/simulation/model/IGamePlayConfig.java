package simulation.model;

import simulation.dao.ITradingDao;

public interface IGamePlayConfig {

    int getId();

    void setId(int id);

    int getLeagueId();

    void setLeagueId(int leagueId);

    IAging getAging();

    void setAging(IAging aging);

    IInjury getInjury();

    void setInjury(IInjury injury);

    IGameResolver getGameResolver();

    void setGameResolver(IGameResolver gameResolver);

    ITrading getTrading();

    void setTrading(ITrading trading);

    void loadTradingDetailsByLeagueId(ITradingDao tradingFactory) throws Exception;

    ITraining getTraining();

    void setTraining(ITraining training);
}
