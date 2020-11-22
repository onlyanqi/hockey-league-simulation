package simulation.model;

import db.data.ITradeOfferDao;

import java.util.List;

public interface ITradeOffer {

    int getLeagueId();

    void setLeagueId(int leagueId);

    int getTradingId();

    void setTradingId(int tradingId);

    int getFromTeamId();

    void setFromTeamId(int fromTeamId);

    int getToTeamId();

    void setToTeamId(int toTeamId);

    int getFromPlayerId();

    void setFromPlayerId(int fromPlayerId);

    int getToPlayerId();

    void setToPlayerId(int toPlayerId);

    int getSeasonId();

    void setSeasonId(int seasonId);

    String getStatus();

    void setStatus(String status);

    void addTradeOffer(ITradeOfferDao tradeOfferFactory) throws Exception;

    List<Integer> getFromPlayerIdList();

    void setFromPlayerIdList(List<Integer> fromPlayerIdList);

    List<Integer> getToPlayerIdList();

    void setToPlayerIdList(List<Integer> toPlayerIdList);

    int getId();

    void setId(int id);
}
