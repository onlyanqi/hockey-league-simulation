package simulation.model;

import db.data.ITradeOfferFactory;

import java.util.ArrayList;
import java.util.List;

public class TradeOfferMock implements ITradeOfferFactory {

    @Override
    public int addTradingOfferDetails(TradeOffer tradeOffer) throws Exception {
        tradeOffer = new TradeOffer();
        tradeOffer.setId(1);
        return tradeOffer.getId();
    }

    public void getTradeOffer(TradeOffer tradeOffer, int from, int to){
        tradeOffer.setId(from);
        tradeOffer.setLeagueId(from);
        tradeOffer.setTradingId(from);
        tradeOffer.setFromTeamId(from);
        tradeOffer.setToTeamId(to);
        tradeOffer.setFromPlayerId(to);
        tradeOffer.setToPlayerId(to);
        tradeOffer.setSeasonId(from);
    }

    @Override
    public List<TradeOffer> loadTradingOfferDetailsByLeagueId(int leagueId) throws Exception {
        List<TradeOffer> tradeOfferList = new ArrayList<>();
        TradeOffer tradeOffer = new TradeOffer();
        getTradeOffer(tradeOffer, 1, 2);
        tradeOffer.setStatus("pending");
        tradeOfferList.add(tradeOffer);
        getTradeOffer(tradeOffer, 3, 4);
        tradeOffer.setStatus("accepted");
        tradeOfferList.add(tradeOffer);
        getTradeOffer(tradeOffer, 5, 6);
        tradeOffer.setStatus("rejected");
        tradeOfferList.add(tradeOffer);
        return tradeOfferList;
    }

    @Override
    public void loadTradingOfferDetailsByTradingId(int tradingId, TradeOffer tradeOffer) throws Exception {
        tradeOffer = new TradeOffer();
        getTradeOffer(tradeOffer, tradingId, 3);
    }

    @Override
    public void loadTradingOfferDetailsById(int tradingOfferId, TradeOffer tradeOffer) throws Exception {
        tradeOffer = new TradeOffer();
        getTradeOffer(tradeOffer, tradingOfferId, 3);
    }
}
