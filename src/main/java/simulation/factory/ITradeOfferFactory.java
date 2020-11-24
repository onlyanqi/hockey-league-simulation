package simulation.factory;

import simulation.dao.ITradeOfferDao;
import simulation.model.ITradeOffer;

public interface ITradeOfferFactory {

    ITradeOffer newTradeOffer();

    ITradeOfferDao newTradeOfferDao();

    ITradeOffer newTradeOfferWithIdDao(int id, ITradeOfferDao tradeOfferDao) throws Exception;
}
