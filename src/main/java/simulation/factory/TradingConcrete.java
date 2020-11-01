package simulation.factory;

import db.dao.TradingDao;
import db.data.ITradingFactory;
import simulation.model.Trading;

public class TradingConcrete {
    public Trading newTrading(){
        return new Trading();
    }

    public ITradingFactory newTradingFactory(){
        return new TradingDao();
    }
}
