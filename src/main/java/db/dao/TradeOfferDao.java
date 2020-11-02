package db.dao;

import db.data.ITradeOfferFactory;
import simulation.factory.ValidationConcrete;
import simulation.model.TradeOffer;
import validator.IValidation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradeOfferDao extends DBExceptionLog implements ITradeOfferFactory {

    private final IValidation validation;

    public TradeOfferDao(){
        ValidationConcrete validationConcrete = new ValidationConcrete();
        validation = validationConcrete.newValidation();
    }

    @Override
    public void addTradeOfferDetails(TradeOffer tradeOffer) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB("AddTradeOffer(?,?,?,?,?,?,?,?,?)");
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
            printLog("TradeOfferDao: addTradeOfferDetails: SQLException: "+sqlException);
            throw sqlException;
        } finally {
            if(validation.isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

    @Override
    public List<TradeOffer> loadTradeOfferDetailsByLeagueId(int leagueId) throws Exception {
        ICallDB callDB = null;
        List<TradeOffer> tradeOfferList;
        try{
            callDB = new CallDB("LoadTradeOfferListByLeagueId(?)");
            callDB.setInputParameterInt(1, leagueId);
            ResultSet rs = callDB.executeLoad();
            if (rs == null) {
                tradeOfferList = null;
            } else {
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
        } catch (SQLException e){
            printLog("TradeOfferDao: loadTradeOfferDetailsByLeagueId: Exception: "+e);
            throw e;
        } finally {
            if(validation.isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
        return tradeOfferList;
    }

    @Override
    public void loadTradeOfferDetailsById(int tradeOfferId, TradeOffer tradeOffer) throws Exception {
        ICallDB callDB = null;
        try{
            callDB = new CallDB("LoadTradeOfferListByLeagueId(?)");
            callDB.setInputParameterInt(1, tradeOfferId);
            ResultSet rs = callDB.executeLoad();
            if (validation.isNotNull(rs)) {
                while (rs.next()) {
                    tradeOffer.setId(rs.getInt(1));
                    tradeOffer.setLeagueId(rs.getInt(2));
                    tradeOffer.setTradingId(rs.getInt(3));
                    tradeOffer.setFromTeamId(rs.getInt(4));
                    tradeOffer.setToTeamId(rs.getInt(5));
                    tradeOffer.setFromPlayerId(rs.getInt(6));
                    tradeOffer.setToPlayerId(rs.getInt(7));
                    tradeOffer.setSeasonId(rs.getInt(8));
                    tradeOffer.setStatus(rs.getString(9));
                }
            }
        } catch (SQLException e){
            printLog("TradeOfferDao: loadTradeOfferDetailsById: Exception: "+e);
            throw e;
        }finally {
            if(validation.isNotNull(callDB)) {
                callDB.closeConnection();
            }
        }
    }

}
