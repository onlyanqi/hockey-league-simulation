package simulation.model;

import db.data.ITradingFactory;
import validator.Validation;

import java.util.*;

public class Trading extends SharedAttributes {

    private List<Integer> currentYearSeasonMonths = new ArrayList<>
            (Arrays.asList(9, 10, 11));

    private List<Integer> nextYearSeasonMonths = new ArrayList<>
            (Arrays.asList(0, 1));

    private Validation validation;
    private Date tradeStartDate;
    private Date tradeEndDate;
    private int leagueId;
    private int lossPoint;
    private double randomTradeOfferChance;
    private int maxPlayersPerTrade;
    private double randomAcceptanceChance;
    private boolean isTradingPeriod;

    public Trading() {
        validation = new Validation();
    }

    public Trading(int tradingId, ITradingFactory factory) throws Exception {
        setId(tradingId);
        factory.loadTradingDetailsByTradingId(tradingId, this);
        validation = new Validation();
    }

    public List<Integer> getCurrentYearSeasonMonths() {
        return currentYearSeasonMonths;
    }

    public void setCurrentYearSeasonMonths(List<Integer> currentYearSeasonMonths) {
        this.currentYearSeasonMonths = currentYearSeasonMonths;
    }

    public List<Integer> getNextYearSeasonMonths() {
        return nextYearSeasonMonths;
    }

    public void setNextYearSeasonMonths(List<Integer> nextYearSeasonMonths) {
        this.nextYearSeasonMonths = nextYearSeasonMonths;
    }

    public void isLeagueInTradingPeriod(Date leagueDate) {
        if (validation.isNotNull(leagueDate)) {
            calTradeEndDateFromLeagueDate(leagueDate);
            int compare = leagueDate.compareTo(tradeEndDate);
            if (compare <= 0) {
                this.isTradingPeriod = true;
                return;
            }
        }
        this.isTradingPeriod = false;
    }

    public Date getTradeStartDate() {
        return tradeStartDate;
    }

    public void setTradeStartDate(Date tradeStartDate) {
        this.tradeStartDate = tradeStartDate;
    }

    public Date getTradeEndDate() {
        return tradeEndDate;
    }

    public void setTradeEndDate(Date tradeEndDate) {
        this.tradeEndDate = tradeEndDate;
    }

    public void calTradeEndDateFromLeagueDate(Date leagueDate) {

        int currentLeagueYear = leagueDate.getYear() + 1900;
        int currentLeagueMonth = leagueDate.getMonth();
        int tradingEndYear = 0;

        if (currentYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingEndYear = currentLeagueYear + 1;
        } else if (nextYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingEndYear = currentLeagueYear;
        }

        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.set(GregorianCalendar.YEAR, tradingEndYear);
        endDateCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        endDateCalendar.set(GregorianCalendar.MONTH, Calendar.FEBRUARY);
        endDateCalendar.set(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
        endDateCalendar.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, -1);
        endDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endDateCalendar.set(Calendar.MINUTE, 0);
        endDateCalendar.set(Calendar.SECOND, 0);
        endDateCalendar.set(Calendar.MILLISECOND, 0);

        this.tradeEndDate = endDateCalendar.getTime();
        this.tradeEndDate.setHours(23);
        this.tradeEndDate.setMinutes(59);
        this.tradeEndDate.setSeconds(59);
    }

    public void calTradeStartDateFromLeagueDate(Date leagueDate) {

        int currentLeagueYear = leagueDate.getYear() + 1900;
        int currentLeagueMonth = leagueDate.getMonth();
        int tradingStartYear = 0;

        if (currentYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingStartYear = currentLeagueYear;
        } else if (nextYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingStartYear = currentLeagueYear - 1;
        }

        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.set(GregorianCalendar.YEAR, tradingStartYear);
        startDateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        startDateCalendar.set(GregorianCalendar.MONTH, Calendar.OCTOBER);
        startDateCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startDateCalendar.set(Calendar.MINUTE, 0);
        startDateCalendar.set(Calendar.SECOND, 0);
        startDateCalendar.set(Calendar.MILLISECOND, 0);

        this.tradeStartDate = startDateCalendar.getTime();
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getLossPoint() {
        return lossPoint;
    }

    public void setLossPoint(int lossPoint) {
        this.lossPoint = lossPoint;
    }

    public double getRandomTradeOfferChance() {
        return randomTradeOfferChance;
    }

    public void setRandomTradeOfferChance(double randomTradeOfferChance) {
        this.randomTradeOfferChance = randomTradeOfferChance;
    }

    public int getMaxPlayersPerTrade() {
        return maxPlayersPerTrade;
    }

    public void setMaxPlayersPerTrade(int maxPlayersPerTrade) {
        this.maxPlayersPerTrade = maxPlayersPerTrade;
    }

    public double getRandomAcceptanceChance() {
        return randomAcceptanceChance;
    }

    public void setRandomAcceptanceChance(double randomAcceptanceChance) {
        this.randomAcceptanceChance = randomAcceptanceChance;
    }

    public boolean isTradingPeriod() {
        return isTradingPeriod;
    }

    public void setTradingPeriod(boolean tradingPeriod) {
        isTradingPeriod = tradingPeriod;
    }

    public void addTrading(ITradingFactory tradingFactory) throws Exception {
        tradingFactory.addTradingDetails(this);
    }
}
