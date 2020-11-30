package simulation.mock;

import simulation.dao.DaoFactoryMock;
import simulation.dao.IDaoFactory;
import simulation.dao.IPlayerDao;
import simulation.dao.ITradeOfferDao;
import simulation.model.IModelFactory;
import simulation.model.ITradeOffer;
import simulation.model.ModelFactory;

import java.util.ArrayList;
import java.util.List;

public class TradeOfferMock implements ITradeOfferDao {

    private final IModelFactory modelFactory;

    public TradeOfferMock() {
        modelFactory = ModelFactory.getInstance();
    }

    @Override
    public void addTradeOfferDetails(ITradeOffer tradeOffer) {
        tradeOffer = modelFactory.createTradeOffer();
        tradeOffer.setId(1);
        tradeOffer.setSeasonId(1);
        tradeOffer.setTradingId(1);
    }

    public void getTradeOffer(ITradeOffer tradeOffer, int from, int to) throws Exception {
        IDaoFactory daoFactory = DaoFactoryMock.getInstance();
        IPlayerDao playerDao = daoFactory.createPlayerDao();

        tradeOffer.setId(from);
        tradeOffer.setLeagueId(from);
        tradeOffer.setTradingId(from);
        tradeOffer.setFromTeamId(from);
        tradeOffer.setToTeamId(to);
        tradeOffer.setFromPlayerId(to);
        tradeOffer.setToPlayerId(to);
        tradeOffer.setSeasonId(from);
        tradeOffer.setStatus("pending");
        List<Integer> fromPlayerIdList = new ArrayList<>();
        fromPlayerIdList.add(1);
        tradeOffer.setFromPlayerIdList(fromPlayerIdList);
        List<Integer> toPlayerIdList = new ArrayList<>();
        toPlayerIdList.add(2);
        tradeOffer.setToPlayerIdList(toPlayerIdList);
    }

    @Override
    public List<ITradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception {
        List<ITradeOffer> tradeOfferList = new ArrayList<>();
        ITradeOffer tradeOffer = modelFactory.createTradeOffer();
        getTradeOffer(tradeOffer, 1, 2);
        tradeOffer.setStatus("pending");
        tradeOfferList.add(tradeOffer);
        tradeOffer = modelFactory.createTradeOffer();
        getTradeOffer(tradeOffer, 3, 4);
        tradeOffer.setStatus("accepted");
        tradeOfferList.add(tradeOffer);
        tradeOffer = modelFactory.createTradeOffer();
        getTradeOffer(tradeOffer, 5, 6);
        tradeOffer.setStatus("rejected");
        tradeOfferList.add(tradeOffer);
        return tradeOfferList;
    }

    @Override
    public void loadTradeOfferDetailsById(int tradingOfferId, ITradeOffer tradeOffer) throws Exception {
        getTradeOffer(tradeOffer, tradingOfferId, 3);
    }


}
