package simulation.serializers.ModelsForDeserialization.model;

import java.util.List;

public class TradeOffer extends SharedAttributes {

    public int leagueId;
    public int tradingId;
    public int fromTeamId;
    public int toTeamId;
    public int fromPlayerId;
    public int toPlayerId;
    public List<Integer> fromPlayerIdList;
    public List<Integer> toPlayerIdList;

    public int seasonId;
    public String status;

}
