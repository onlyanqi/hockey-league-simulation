package simulation.factory;

import simulation.model.ITrading;
import simulation.model.Trading;

public interface ITradingFactory {

    ITrading newTrading();

}
