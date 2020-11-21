package db.data;

import simulation.model.ITrading;

public interface ITradingDao {

    int addTradingDetails(ITrading trading) throws Exception;

    ITrading loadTradingDetailsByLeagueId(int leagueId) throws Exception;

    void loadTradingDetailsByTradingId(int tradingId, ITrading trading) throws Exception;
}
