package simulation.model;

import java.time.LocalDate;

public abstract class Events {

    public LocalDate regularSeasonStartDate;
    public LocalDate tradeDeadlineDate;
    public LocalDate endOfRegularSeason;
    public LocalDate playOffStartDate;
    public LocalDate lastDayStanleyCupFinals;
    public LocalDate nextSeasonDate;
    public Events() {

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

    public abstract void initializeRegularSeasonStartDate();

    public abstract void initializeTradeDeadlineDate();

    public abstract void initializeEndOfRegularSeason();

    public abstract void initializePlayOffStartDate();

    public abstract void initializeLastDayStanleyCupFinals();

    public abstract void initializeNextSeasonDate();
}
