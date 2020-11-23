package db.dao;

import db.data.ITradeOfferDao;
import simulation.model.ITradeOffer;

import java.util.List;

public class TradeOfferDao extends DBExceptionLog implements ITradeOfferDao {


    @Override
    public void addTradeOfferDetails(ITradeOffer tradeOffer) throws Exception {

    }

    @Override
    public List<ITradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception {
        return null;
    }

    @Override
    public void loadTradeOfferDetailsById(int tradingOfferId, ITradeOffer tradeOffer) throws Exception {

    }
}
