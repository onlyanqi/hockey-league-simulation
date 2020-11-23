package simulation.factory;

import db.dao.TradeOfferDao;
import db.data.ITradeOfferDao;
import simulation.model.ITradeOffer;
import simulation.model.TradeOffer;

public interface ITradeOfferFactory {

    ITradeOffer newTradeOffer();

    ITradeOfferDao newTradeOfferDao();

    ITradeOffer newTradeOfferWithIdDao(int id, ITradeOfferDao tradeOfferDao) throws Exception;
}
