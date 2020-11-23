package db.dao;

import db.data.ITradingDao;
import simulation.model.ITrading;

public class TradingDao extends DBExceptionLog implements ITradingDao {


    @Override
    public int addTradingDetails(ITrading trading) throws Exception {
        return 0;
    }

    @Override
    public ITrading loadTradingDetailsByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public void loadTradingDetailsByTradingId(int tradingId, ITrading trading) throws Exception {

    }
}
