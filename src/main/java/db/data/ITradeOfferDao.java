package db.data;

import simulation.model.ITradeOffer;
import simulation.model.TradeOffer;

import java.util.List;

public interface ITradeOfferDao {

    void addTradeOfferDetails(ITradeOffer tradeOffer) throws Exception;

    List<ITradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception;

    void loadTradeOfferDetailsById(int tradingOfferId, ITradeOffer tradeOffer) throws Exception;

}
