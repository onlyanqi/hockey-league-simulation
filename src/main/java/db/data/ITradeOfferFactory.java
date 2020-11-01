package db.data;

import simulation.model.TradeOffer;

import java.util.List;

public interface ITradeOfferFactory {

    int addTradeOfferDetails(TradeOffer tradeOffer) throws Exception;
    List loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception;
    void loadTradeOfferDetailsById(int tradingOfferId, TradeOffer tradeOffer) throws Exception;

}
