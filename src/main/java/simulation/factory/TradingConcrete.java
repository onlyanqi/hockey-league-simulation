package simulation.factory;

import simulation.model.ITrading;
import simulation.model.Trading;

public class TradingConcrete implements ITradingFactory{
    public ITrading newTrading() {
        return new Trading();
    }
}
