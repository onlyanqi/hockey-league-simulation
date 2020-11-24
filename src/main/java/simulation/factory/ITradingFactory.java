package simulation.factory;

import simulation.dao.ITradingDao;
import simulation.model.ITrading;

public interface ITradingFactory {

    ITrading newTrading();

    ITradingDao newTradingDao();

    ITrading newTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception;

}
