package simulation.factory;

import db.dao.TradingDao;
import db.data.ITradingDao;
import simulation.model.ITrading;
import simulation.model.Trading;

public interface ITradingFactory {

    ITrading newTrading();

    ITradingDao newTradingDao();

    ITrading newTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception;

}
