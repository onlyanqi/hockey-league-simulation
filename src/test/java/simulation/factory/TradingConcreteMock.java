package simulation.factory;

import db.dao.TradingDao;
import db.data.ITradingDao;
import simulation.mock.TradingMock;
import simulation.model.ITrading;
import simulation.model.Trading;

public class TradingConcreteMock implements ITradingFactory {

    public ITrading newTrading() {
        return new Trading();
    }

    public ITradingDao newTradingDao(){
        return new TradingMock();
    }

    public ITrading newTradingWithIdDao(int id, ITradingDao tradingDao) throws Exception {
        return new Trading(id, tradingDao);
    }

}
