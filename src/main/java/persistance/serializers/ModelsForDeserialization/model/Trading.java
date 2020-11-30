package persistance.serializers.ModelsForDeserialization.model;

import java.util.*;

public class Trading extends SharedAttributes {

    public List<Integer> currentYearSeasonMonths = new ArrayList<>
            (Arrays.asList(9, 10, 11));

    public List<Integer> nextYearSeasonMonths = new ArrayList<>
            (Arrays.asList(0, 1));


    public Date tradeStartDate;
    public Date tradeEndDate;
    public int leagueId;
    public int lossPoint;
    public double randomTradeOfferChance;
    public int maxPlayersPerTrade;
    public double randomAcceptanceChance;
    public boolean isTradingPeriod;
    public Map<String, Double> gmTable;
}
