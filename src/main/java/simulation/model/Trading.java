package simulation.model;

import persistance.dao.ITradingDao;

import java.util.*;

public class Trading extends SharedAttributes implements ITrading {

    private List<Integer> currentYearSeasonMonths = new ArrayList<>
            (Arrays.asList(9, 10, 11));

    private List<Integer> nextYearSeasonMonths = new ArrayList<>
            (Arrays.asList(0, 1));


    private Date tradeStartDate;
    private Date tradeEndDate;
    private int leagueId;
    private int lossPoint;
    private double randomTradeOfferChance;
    private int maxPlayersPerTrade;
    private double randomAcceptanceChance;
    private boolean isTradingPeriod;
    private Map<String, Double> gmTable = new HashMap<>();

    public Trading() {
        setId(System.identityHashCode(this));
    }

    public Trading(int tradingId, ITradingDao factory) throws Exception {
        setId(tradingId);
        factory.loadTradingDetailsByTradingId(tradingId, this);
    }

    public Trading(persistance.serializers.ModelsForDeserialization.model.Trading trading) {
        this.currentYearSeasonMonths = trading.currentYearSeasonMonths;
        this.nextYearSeasonMonths = trading.nextYearSeasonMonths;
        this.tradeStartDate = trading.tradeStartDate;
        this.tradeEndDate = trading.tradeEndDate;
        this.leagueId = trading.leagueId;
        this.lossPoint = trading.lossPoint;
        this.randomTradeOfferChance = trading.randomTradeOfferChance;
        this.maxPlayersPerTrade = trading.maxPlayersPerTrade;
        this.randomAcceptanceChance = trading.randomAcceptanceChance;
        this.isTradingPeriod = trading.isTradingPeriod;
        this.gmTable = trading.gmTable;
        this.setId(trading.id);
        this.setName(trading.name);
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
        if (leagueDate == null) {
            this.isTradingPeriod = false;
        } else {
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

        int zero = 0;
        int yearInit = 1900;
        int twentyThree = 23;
        int fiftyNine = 59;
        int one = 1;
        int negativeOne = -1;

        int currentLeagueYear = leagueDate.getYear() + yearInit;
        int currentLeagueMonth = leagueDate.getMonth();
        int tradingEndYear = zero;

        if (currentYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingEndYear = currentLeagueYear + one;
        } else if (nextYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingEndYear = currentLeagueYear;
        }

        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.set(GregorianCalendar.YEAR, tradingEndYear);
        endDateCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        endDateCalendar.set(GregorianCalendar.MONTH, Calendar.FEBRUARY);
        endDateCalendar.set(GregorianCalendar.DAY_OF_WEEK, Calendar.MONDAY);
        endDateCalendar.set(GregorianCalendar.DAY_OF_WEEK_IN_MONTH, negativeOne);
        endDateCalendar.set(Calendar.HOUR_OF_DAY, zero);
        endDateCalendar.set(Calendar.MINUTE, zero);
        endDateCalendar.set(Calendar.SECOND, zero);
        endDateCalendar.set(Calendar.MILLISECOND, zero);

        this.tradeEndDate = endDateCalendar.getTime();
        this.tradeEndDate.setHours(twentyThree);
        this.tradeEndDate.setMinutes(fiftyNine);
        this.tradeEndDate.setSeconds(fiftyNine);
    }

    public void calTradeStartDateFromLeagueDate(Date leagueDate) {

        int zero = 0;
        int yearInit = 1900;
        int one = 1;
        int currentLeagueYear = leagueDate.getYear() + yearInit;
        int currentLeagueMonth = leagueDate.getMonth();
        int tradingStartYear = zero;

        if (currentYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingStartYear = currentLeagueYear;
        } else if (nextYearSeasonMonths.contains(currentLeagueMonth)) {
            tradingStartYear = currentLeagueYear - one;
        }

        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.set(GregorianCalendar.YEAR, tradingStartYear);
        startDateCalendar.set(Calendar.DAY_OF_MONTH, one);
        startDateCalendar.set(GregorianCalendar.MONTH, Calendar.OCTOBER);
        startDateCalendar.set(Calendar.HOUR_OF_DAY, zero);
        startDateCalendar.set(Calendar.MINUTE, zero);
        startDateCalendar.set(Calendar.SECOND, zero);
        startDateCalendar.set(Calendar.MILLISECOND, zero);

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

    public void addTrading(ITradingDao tradingFactory) throws Exception {
        tradingFactory.addTradingDetails(this);
    }

    public Map<String, Double> getGmTable() {
        return gmTable;
    }

    public void setGmTable(Map<String, Double> gmTable) {
        this.gmTable = gmTable;
    }
}
