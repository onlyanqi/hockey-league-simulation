package db.data;

import simulation.model.Trading;

public interface ITradingFactory {

    int addTradingDetails(Trading trading);

    void loadTradingDetailsByLeagueId(int leagueId, Trading trading);

    void loadTradingDetailsByTradingId(int tradingId, Trading trading);

}
