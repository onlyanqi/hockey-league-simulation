package db.data;

import simulation.model.Trading;

public interface ITradingFactory {

    int addTradingDetails(Trading trading) throws Exception;
    Trading loadTradingDetailsByLeagueId(int leagueId) throws Exception;
    void loadTradingDetailsByTradingId(int tradingId, Trading trading) throws Exception;
}
