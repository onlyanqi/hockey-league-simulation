package simulation.model;

import persistance.dao.ITradingDao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ITrading {

    List<Integer> getCurrentYearSeasonMonths();

    void setCurrentYearSeasonMonths(List<Integer> currentYearSeasonMonths);

    List<Integer> getNextYearSeasonMonths();

    void setNextYearSeasonMonths(List<Integer> nextYearSeasonMonths);

    void isLeagueInTradingPeriod(Date leagueDate);

    Date getTradeStartDate();

    void setTradeStartDate(Date tradeStartDate);

    Date getTradeEndDate();

    void setTradeEndDate(Date tradeEndDate);

    void calTradeEndDateFromLeagueDate(Date leagueDate);

    void calTradeStartDateFromLeagueDate(Date leagueDate);

    int getLeagueId();

    void setLeagueId(int leagueId);

    int getLossPoint();

    void setLossPoint(int lossPoint);

    double getRandomTradeOfferChance();

    void setRandomTradeOfferChance(double randomTradeOfferChance);

    int getMaxPlayersPerTrade();

    void setMaxPlayersPerTrade(int maxPlayersPerTrade);

    double getRandomAcceptanceChance();

    void setRandomAcceptanceChance(double randomAcceptanceChance);

    boolean isTradingPeriod();

    void setTradingPeriod(boolean tradingPeriod);

    void addTrading(ITradingDao tradingFactory) throws Exception;

    Map<String, Double> getGmTable();

    void setGmTable(Map<String, Double> gmTable);

    int getId();

    void setId(int id);
}
