package simulation.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;


public class NHLEvents {

    public NHLEvents() {
        initializeEndOfRegularSeason();
        initializeTradeDeadlineDate();
        initializeRegularSeasonStartDate();
        initializePlayOffStartDate();
        initializeLastDayStanleyCupFinals();
        initializeNextSeasonDate();
    }

    public LocalDate regularSeasonStartDate;
    public LocalDate tradeDeadlineDate;
    public LocalDate endOfRegularSeason;
    public LocalDate playOffStartDate;
    public LocalDate lastDayStanleyCupFinals;
    public LocalDate nextSeasonDate;

    public LocalDate getNextSeasonDate() {
        return nextSeasonDate;
    }

    public LocalDate getRegularSeasonStartDate() {
        return regularSeasonStartDate;
    }

    public void setRegularSeasonStartDate(LocalDate regularSeasonStartDate) {
        this.regularSeasonStartDate = regularSeasonStartDate;
    }

    public void setTradeDeadlineDate(LocalDate tradeDeadlineDate) {
        this.tradeDeadlineDate = tradeDeadlineDate;
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


    public void setLastDayStanleyCupFinals(LocalDate lastDayStanleyCupFinals) {
        this.lastDayStanleyCupFinals = lastDayStanleyCupFinals;
    }

    public void setNextSeasonDate(LocalDate nextSeasonDate) {
        this.nextSeasonDate = nextSeasonDate;
    }

    public boolean checkTradeDeadlinePassed(LocalDate currentDate) {
        if(currentDate.compareTo(tradeDeadlineDate) > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEndOfRegularSeason(LocalDate currentDate) {
        if(currentDate.equals(endOfRegularSeason)){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkRegularSeasonPassed(LocalDate currentDate) {
        if(currentDate.compareTo(endOfRegularSeason) > 0){
            return true;
        } else {
            return false;
        }
    }

    public void initializeRegularSeasonStartDate() {
        LocalDate seasonStartDate = LocalDate.of(LocalDate.now().getYear(), Month.SEPTEMBER, 30);
        setRegularSeasonStartDate(seasonStartDate);
    }

    public void initializeTradeDeadlineDate() {
        LocalDate  endOfMonth = LocalDate.of(LocalDate.now().getYear()+1, Month.FEBRUARY,1);
        LocalDate tradeDeadlineDate = endOfMonth.with( TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY ) );
        setTradeDeadlineDate(tradeDeadlineDate);
    }

    public void initializeEndOfRegularSeason() {
        LocalDate  nextYearDate= LocalDate.of(LocalDate.now().getYear()+1, Month.APRIL,1);
        LocalDate endOfRegularSeason = nextYearDate.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.SATURDAY));
        setEndOfRegularSeason(endOfRegularSeason);
    }

    public void initializePlayOffStartDate() {
        LocalDate  nextYearDate = LocalDate.of(LocalDate.now().getYear()+1, Month.APRIL,1);
        LocalDate playOffStartDate = nextYearDate.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.WEDNESDAY));
        setPlayOffStartDate(playOffStartDate);
    }

    public void initializeLastDayStanleyCupFinals() {
        LocalDate  stanleyCupLastDay = LocalDate.of(LocalDate.now().getYear()+1, Month.JUNE,1);
        setLastDayStanleyCupFinals(stanleyCupLastDay);
    }

    public void initializeNextSeasonDate() {
        LocalDate  nextSeasonStartDate = LocalDate.of(LocalDate.now().getYear()+1, Month.SEPTEMBER,29);
        setNextSeasonDate(nextSeasonStartDate);
    }

}
