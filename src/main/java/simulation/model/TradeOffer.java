package simulation.model;

import db.data.ITradeOfferFactory;

public class TradeOffer extends SharedAttributes {

    public TradeOffer(){}

    public TradeOffer(int tradingOfferId, ITradeOfferFactory factory) throws Exception {
        setId(tradingOfferId);
        factory.loadTradingOfferDetailsById(tradingOfferId, this);
    }

    private int leagueId;
    private int tradingId;
    private int fromTeamId;
    private int toTeamId;
    private int fromPlayerId;
    private int toPlayerId;
    private int seasonId;
    private String status;

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




}
