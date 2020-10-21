package simulation.factory;
import simulation.model.Trading;

public class TradingConcrete {
    public Trading newTrading(){
        return new Trading();
    }
}
