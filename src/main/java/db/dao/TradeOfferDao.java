package db.dao;

import db.data.ITradeOfferFactory;
import simulation.model.TradeOffer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeOfferDao implements ITradeOfferFactory {

    @Override
    public int addTradingOfferDetails(TradeOffer tradeOffer) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB("AddTradingOffer(?,?,?,?,?,?,?,?,?)");
            callDB.setInputParameterInt(1, tradeOffer.getLeagueId());
            callDB.setInputParameterInt(2, tradeOffer.getSeasonId());
            callDB.setInputParameterInt(3, tradeOffer.getFromTeamId());
            callDB.setInputParameterInt(4, tradeOffer.getToTeamId());
            callDB.setInputParameterInt(5, tradeOffer.getFromPlayerId());
            callDB.setInputParameterInt(6, tradeOffer.getToPlayerId());
            callDB.setInputParameterInt(7, tradeOffer.getTradingId());
            callDB.setInputParameterString(8, tradeOffer.getStatus());
            callDB.setOutputParameterInt(9);
            callDB.execute();
            tradeOffer.setId(callDB.returnOutputParameterInt(9));

        } catch (SQLException sqlException){
            throw sqlException;
        } finally {
            callDB.closeConnection();
        }
        return tradeOffer.getId();
    }

    @Override
    public List loadTradingOfferDetailsByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        List<TradeOffer> tradeOfferList = null;
        try{
            callDB = new CallDB("LoadTradingDetailsByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (rs != null) {
                tradeOfferList = new ArrayList<>();
                while (rs.next()) {
                    TradeOffer tradeOffer = new TradeOffer();
                    tradeOffer.setId(rs.getInt(1));
                    tradeOffer.setLeagueId(rs.getInt(2));
                    tradeOffer.setTradingId(rs.getInt(3));
                    tradeOffer.setFromTeamId(rs.getInt(4));
                    tradeOffer.setToTeamId(rs.getInt(5));
                    tradeOffer.setFromPlayerId(rs.getInt(6));
                    tradeOffer.setToPlayerId(rs.getInt(7));
                    tradeOffer.setSeasonId(rs.getInt(8));
                    tradeOffer.setStatus(rs.getString(9));
                    tradeOfferList.add(tradeOffer);
                }
            }
        } catch (Exception e){
            throw e;
        }
        return tradeOfferList;
    }


    @Override
    public void loadTradingOfferDetailsByTradingId(int tradingId, TradeOffer tradeOffer) throws Exception {

    }

    @Override
    public void loadTradingOfferDetailsById(int tradingOfferId, TradeOffer tradeOffer) throws Exception {

    }
}
