package simulation.factory;

import db.data.ITradeOfferFactory;
import simulation.model.TradeOffer;

public class TradeOfferConcrete {
    public TradeOffer newTradeOffer() {
        return new TradeOffer();
    }
}
