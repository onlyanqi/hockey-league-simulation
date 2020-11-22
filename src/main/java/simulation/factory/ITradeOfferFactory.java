package simulation.factory;

import simulation.model.ITradeOffer;
import simulation.model.TradeOffer;

public interface ITradeOfferFactory {

    ITradeOffer newTradeOffer();

}
