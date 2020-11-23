package simulation.factory;

import db.dao.TradeOfferDao;
import db.data.ITradeOfferDao;
import simulation.model.ITradeOffer;
import simulation.model.TradeOffer;

public class TradeOfferConcreteMock implements ITradeOfferFactory{

    public ITradeOffer newTradeOffer() {
        return new TradeOffer();
    }

    public ITradeOfferDao newTradeOfferDao(){
        return new TradeOfferDao();
    }

    public ITradeOffer newTradeOfferWithIdDao(int id, ITradeOfferDao tradeOfferDao) throws Exception {
        return new TradeOffer(id, tradeOfferDao);
    }

}
