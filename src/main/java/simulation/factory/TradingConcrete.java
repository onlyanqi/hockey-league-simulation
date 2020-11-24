package simulation.factory;

import simulation.dao.TradingDao;
import simulation.dao.ITradingDao;
import simulation.model.ITrading;
import simulation.model.Trading;

public class TradingConcrete implements ITradingFactory{

    public ITrading newTrading() {
        return new Trading();
    }

    public ITradingDao newTradingDao(){
        return new TradingDao();
    }

    public ITrading newTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception {
        return new Trading(id, tradingDao);
    }

}
