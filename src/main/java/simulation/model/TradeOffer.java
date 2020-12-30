package simulation.model;

import persistance.dao.ITradeOfferDao;

import java.util.ArrayList;
import java.util.List;

public class TradeOffer extends SharedAttributes implements ITradeOffer {

    private int leagueId;
    private int tradingId;
    private int fromTeamId;
    private int toTeamId;
    private int fromPlayerId;
    private int toPlayerId;
    private List<Integer> fromPlayerIdList = new ArrayList<>();
    private List<Integer> toPlayerIdList = new ArrayList<>();

    private int seasonId;
    private String status;

    public TradeOffer() {
        setId(System.identityHashCode(this));
    }

    public TradeOffer(int tradingOfferId, ITradeOfferDao factory) throws Exception {
        setId(tradingOfferId);
        factory.loadTradeOfferDetailsById(tradingOfferId, this);
    }

    public TradeOffer(persistance.serializers.ModelsForDeserialization.model.TradeOffer tradeOffer) {
        this.leagueId = tradeOffer.leagueId;
        this.tradingId = tradeOffer.tradingId;
        this.fromPlayerId = tradeOffer.fromPlayerId;
        this.toTeamId = tradeOffer.toTeamId;
        this.fromPlayerId = tradeOffer.fromPlayerId;
        this.toPlayerId = tradeOffer.toPlayerId;
        this.fromPlayerIdList = tradeOffer.fromPlayerIdList;
        this.toPlayerIdList = tradeOffer.toPlayerIdList;
        this.seasonId = tradeOffer.seasonId;
        this.status = tradeOffer.status;
        this.setName(tradeOffer.name);
        this.setId(tradeOffer.id);
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getTradingId() {
        return tradingId;
    }

    public void setTradingId(int tradingId) {
        this.tradingId = tradingId;
    }

    public int getFromTeamId() {
        return fromTeamId;
    }

    public void setFromTeamId(int fromTeamId) {
        this.fromTeamId = fromTeamId;
    }

    public int getToTeamId() {
        return toTeamId;
    }

    public void setToTeamId(int toTeamId) {
        this.toTeamId = toTeamId;
    }

    public int getFromPlayerId() {
        return fromPlayerId;
    }

    public void setFromPlayerId(int fromPlayerId) {
        this.fromPlayerId = fromPlayerId;
    }

    public int getToPlayerId() {
        return toPlayerId;
    }

    public void setToPlayerId(int toPlayerId) {
        this.toPlayerId = toPlayerId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addTradeOffer(ITradeOfferDao tradeOfferFactory) throws Exception {
        tradeOfferFactory.addTradeOfferDetails(this);
    }

    public List<Integer> getFromPlayerIdList() {
        return fromPlayerIdList;
    }

    public void setFromPlayerIdList(List<Integer> fromPlayerIdList) {
        this.fromPlayerIdList = fromPlayerIdList;
    }

    public List<Integer> getToPlayerIdList() {
        return toPlayerIdList;
    }

    public void setToPlayerIdList(List<Integer> toPlayerIdList) {
        this.toPlayerIdList = toPlayerIdList;
    }
}
