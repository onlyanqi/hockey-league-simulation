package simulation.factory;

import db.data.ITradingFactory;
import simulation.model.Trading;

public class TradingConcrete {
    public Trading newTrading() {
        return new Trading();
    }
}
