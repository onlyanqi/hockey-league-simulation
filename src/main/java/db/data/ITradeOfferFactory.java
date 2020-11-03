package db.data;

import simulation.model.TradeOffer;

import java.util.List;

public interface ITradeOfferFactory {

    void addTradeOfferDetails(TradeOffer tradeOffer) throws Exception;

    List<TradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception;

    void loadTradeOfferDetailsById(int tradingOfferId, TradeOffer tradeOffer) throws Exception;

}
