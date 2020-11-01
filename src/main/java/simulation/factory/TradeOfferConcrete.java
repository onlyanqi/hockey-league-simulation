package simulation.factory;

import db.dao.TradeOfferDao;
import db.data.ITradeOfferFactory;
import simulation.model.TradeOffer;

public class TradeOfferConcrete {
    public TradeOffer newTradeOffer(){
        return new TradeOffer();
    }

    public ITradeOfferFactory newTradeOfferFactory(){
        return new TradeOfferDao();
    }
}
