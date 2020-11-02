package simulation.mock;

import db.data.ITradeOfferFactory;
import simulation.model.TradeOffer;

import java.util.ArrayList;
import java.util.List;

public class TradeOfferMock implements ITradeOfferFactory {

    @Override
    public int addTradeOfferDetails(TradeOffer tradeOffer) throws Exception {
        tradeOffer = new TradeOffer();
        tradeOffer.setId(1);
        return tradeOffer.getId();
    }

    public void getTradeOffer(TradeOffer tradeOffer, int from, int to) {
        tradeOffer.setId(from);
        tradeOffer.setLeagueId(from);
        tradeOffer.setTradingId(from);
        tradeOffer.setFromTeamId(from);
        tradeOffer.setToTeamId(to);
        tradeOffer.setFromPlayerId(to);
        tradeOffer.setToPlayerId(to);
        tradeOffer.setSeasonId(from);
        tradeOffer.setStatus("pending");
    }

    @Override
    public List<TradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception {
        List<TradeOffer> tradeOfferList = new ArrayList<>();
        TradeOffer tradeOffer = new TradeOffer();
        getTradeOffer(tradeOffer, 1, 2);
        tradeOffer.setStatus("pending");
        tradeOfferList.add(tradeOffer);
        tradeOffer = new TradeOffer();
        getTradeOffer(tradeOffer, 3, 4);
        tradeOffer.setStatus("accepted");
        tradeOfferList.add(tradeOffer);
        tradeOffer = new TradeOffer();
        getTradeOffer(tradeOffer, 5, 6);
        tradeOffer.setStatus("rejected");
        tradeOfferList.add(tradeOffer);
        return tradeOfferList;
    }

    /*
    @Override
    public void loadTradeOfferDetailsByTradingId(int tradingId, TradeOffer tradeOffer) throws Exception {
        tradeOffer = new TradeOffer();
        getTradeOffer(tradeOffer, tradingId, 3);
    }
*/

    @Override
    public void loadTradeOfferDetailsById(int tradingOfferId, TradeOffer tradeOffer) throws Exception {
        getTradeOffer(tradeOffer, tradingOfferId, 3);
    }


}
