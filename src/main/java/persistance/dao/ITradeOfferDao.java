package persistance.dao;

import simulation.model.ITradeOffer;

import java.util.List;

public interface ITradeOfferDao {

    void addTradeOfferDetails(ITradeOffer tradeOffer) throws Exception;

    List<ITradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception;

    void loadTradeOfferDetailsById(int tradingOfferId, ITradeOffer tradeOffer) throws Exception;

}
