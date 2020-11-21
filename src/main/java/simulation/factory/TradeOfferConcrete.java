package simulation.factory;

import simulation.model.ITradeOffer;
import simulation.model.TradeOffer;

public class TradeOfferConcrete implements ITradeOfferFactory {
    public ITradeOffer newTradeOffer() {
        return new TradeOffer();
    }
}
