package db.data;

import simulation.model.TradeOffer;

import java.util.List;

public interface ITradeOfferFactory {

    int addTradingOfferDetails(TradeOffer trading) throws Exception;
    List loadTradingOfferDetailsByLeagueId(int leagueId) throws Exception;
    void loadTradingOfferDetailsByTradingId(int tradingId, TradeOffer tradeOffer) throws Exception;
    void loadTradingOfferDetailsById(int tradingOfferId, TradeOffer tradeOffer) throws Exception;

}
