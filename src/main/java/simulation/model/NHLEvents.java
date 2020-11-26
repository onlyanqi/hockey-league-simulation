package simulation.model;

import simulation.dao.IEventDao;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;


public class NHLEvents implements INHLEvents{

    public int id;
    public LocalDate regularSeasonStartDate;
    public LocalDate tradeDeadlineDate;
    public LocalDate endOfRegularSeason;
    public LocalDate playOffStartDate;
    public LocalDate lastDayStanleyCupFinals;
    public LocalDate nextSeasonDate;
    public LocalDate playerDraftDate;
    public int currentYear;

    public NHLEvents() {
        setId(System.identityHashCode(this));
        this.currentYear = LocalDate.now().getYear();
        initializeEndOfRegularSeason();
        initializeTradeDeadlineDate();
        initializeRegularSeasonStartDate();
        initializePlayOffStartDate();
        initializeLastDayStanleyCupFinals();
        initializeNextSeasonDate();
        initializeDraftDate();
    }

    public NHLEvents(int currentYear){
        setId(System.identityHashCode(this));
        this.currentYear = currentYear;
        initializeEndOfRegularSeason();
        initializeTradeDeadlineDate();
        initializeRegularSeasonStartDate();
        initializePlayOffStartDate();
        initializeLastDayStanleyCupFinals();
        initializeNextSeasonDate();
        initializeDraftDate();
    }

    public NHLEvents(int leagueId, IEventDao iEventDao) throws Exception {
        iEventDao.loadEventByLeagueId(leagueId, this);
    }

    public NHLEvents(simulation.serializers.ModelsForDeserialization.model.NHLEvents nhlEvents){
        this.id =nhlEvents.id;
        this.regularSeasonStartDate = nhlEvents.regularSeasonStartDate;
        this.tradeDeadlineDate = nhlEvents.tradeDeadlineDate;
        this.endOfRegularSeason = nhlEvents.endOfRegularSeason;
        this.playOffStartDate = nhlEvents.playOffStartDate;
        this.lastDayStanleyCupFinals = nhlEvents.lastDayStanleyCupFinals;
        this.nextSeasonDate = nhlEvents.nextSeasonDate;
        this.currentYear = nhlEvents.currentYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getNextSeasonDate() {
        return nextSeasonDate;
    }

    public void setNextSeasonDate(LocalDate nextSeasonDate) {
        this.nextSeasonDate = nextSeasonDate;
    }

    public LocalDate getRegularSeasonStartDate() {
        return regularSeasonStartDate;
    }

    public void setRegularSeasonStartDate(LocalDate regularSeasonStartDate) {
        this.regularSeasonStartDate = regularSeasonStartDate;
    }

    public LocalDate getEndOfRegularSeason() {
        return endOfRegularSeason;
    }

    public void setEndOfRegularSeason(LocalDate endOfRegularSeason) {
        this.endOfRegularSeason = endOfRegularSeason;
    }

    public LocalDate getPlayOffStartDate() {
        return playOffStartDate;
    }

    public void setPlayOffStartDate(LocalDate playOffStartDate) {
        this.playOffStartDate = playOffStartDate;
    }

    public LocalDate getTradeDeadlineDate() {
        return tradeDeadlineDate;
    }

    public void setTradeDeadlineDate(LocalDate tradeDeadlineDate) {
        this.tradeDeadlineDate = tradeDeadlineDate;
    }

    public LocalDate getLastDayStanleyCupFinals() {
        return lastDayStanleyCupFinals;
    }

    public void setLastDayStanleyCupFinals(LocalDate lastDayStanleyCupFinals) {
        this.lastDayStanleyCupFinals = lastDayStanleyCupFinals;
    }

    public LocalDate getPlayerDraftDate() {
        return playerDraftDate;
    }

    public void setPlayerDraftDate(LocalDate playerDraftDate) {
        this.playerDraftDate = playerDraftDate;
    }

    public boolean checkTradeDeadlinePassed(LocalDate currentDate) {
        if (currentDate.compareTo(tradeDeadlineDate) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkEndOfRegularSeason(LocalDate currentDate) {
        if (currentDate.equals(endOfRegularSeason)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkRegularSeasonPassed(LocalDate currentDate) {
        if (currentDate.compareTo(endOfRegularSeason) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void initializeRegularSeasonStartDate() {
        LocalDate seasonStartDate = LocalDate.of(currentYear, Month.OCTOBER, 01);
        setRegularSeasonStartDate(seasonStartDate);
    }

    public void initializeTradeDeadlineDate() {
        LocalDate endOfMonth = LocalDate.of(currentYear + 1, Month.FEBRUARY, 1);
        LocalDate tradeDeadlineDate = endOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        setTradeDeadlineDate(tradeDeadlineDate);
    }

    public void initializeEndOfRegularSeason() {
        LocalDate nextYearDate = LocalDate.of(currentYear + 1, Month.APRIL, 1);
        LocalDate endOfRegularSeason = nextYearDate.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.SATURDAY));
        setEndOfRegularSeason(endOfRegularSeason);
    }

    public void initializePlayOffStartDate() {
        LocalDate nextYearDate = LocalDate.of(currentYear + 1, Month.APRIL, 1);
        LocalDate playOffStartDate = nextYearDate.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.WEDNESDAY));
        setPlayOffStartDate(playOffStartDate);
    }

    public void initializeLastDayStanleyCupFinals() {
        LocalDate stanleyCupLastDay = LocalDate.of(currentYear + 1, Month.JUNE, 1);
        setLastDayStanleyCupFinals(stanleyCupLastDay);
    }

    public void initializeNextSeasonDate() {
        LocalDate nextSeasonStartDate = LocalDate.of(currentYear + 1, Month.SEPTEMBER, 29);
        setNextSeasonDate(nextSeasonStartDate);
    }

    public void initializeDraftDate(){
        LocalDate playerDraftDate = LocalDate.of(currentYear + 1, Month.JULY, 15);
        setPlayerDraftDate(playerDraftDate);
    }

}
