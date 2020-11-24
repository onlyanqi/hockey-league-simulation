package simulation.factory;

import simulation.dao.TradeOfferDao;
import simulation.dao.ITradeOfferDao;
import simulation.model.ITradeOffer;
import simulation.model.TradeOffer;

public class TradeOfferConcrete implements ITradeOfferFactory {

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
