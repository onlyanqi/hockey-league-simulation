package simulation.mock;

import simulation.dao.ITradeOfferDao;
import simulation.model.ITradeOffer;
import simulation.model.TradeOffer;

import java.util.ArrayList;
import java.util.List;

public class TradeOfferMock implements ITradeOfferDao {

    @Override
    public void addTradeOfferDetails(ITradeOffer tradeOffer) {
        tradeOffer = new TradeOffer();
        tradeOffer.setId(1);
        tradeOffer.setSeasonId(1);
        tradeOffer.setTradingId(1);
    }

    public void getTradeOffer(ITradeOffer tradeOffer, int from, int to) {
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
    public List<ITradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) {
        List<ITradeOffer> tradeOfferList = new ArrayList<>();
        ITradeOffer tradeOffer = new TradeOffer();
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

    @Override
    public void loadTradeOfferDetailsById(int tradingOfferId, ITradeOffer tradeOffer) {
        getTradeOffer(tradeOffer, tradingOfferId, 3);
    }


}
