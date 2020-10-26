package simulation.RegularSeasonEvents;

import java.util.Date;

public class NHLEvents {
    public Date regularSeasonStartDate;
    public Date tradeDeadlineDate;
    public Date endOfRegularSeason;
    public Date playOffStartDate;
    public Date lastDayStanleyCupFinals;
    public Date nextSeasonDate;

    public Date getNextSeasonDate() {
        return nextSeasonDate;
    }

    public void setNextSeasonDate(Date nextSeasonDate) {
        this.nextSeasonDate = nextSeasonDate;
    }

    public Date getRegularSeasonStartDate() {
        return regularSeasonStartDate;
    }

    public void setRegularSeasonStartDate(Date regularSeasonStartDate) {
        this.regularSeasonStartDate = regularSeasonStartDate;
    }

    public Date getTradeDeadlineDate() {
        return tradeDeadlineDate;
    }

    public void setTradeDeadlineDate(Date tradeDeadlineDate) {
        this.tradeDeadlineDate = tradeDeadlineDate;
    }

    public Date getEndOfRegularSeason() {
        return endOfRegularSeason;
    }

    public void setEndOfRegularSeason(Date endOfRegularSeason) {
        this.endOfRegularSeason = endOfRegularSeason;
    }

    public Date getPlayOffStartDate() {
        return playOffStartDate;
    }

    public void setPlayOffStartDate(Date playOffStartDate) {
        this.playOffStartDate = playOffStartDate;
    }

    public Date getLastDayStanleyCupFinals() {
        return lastDayStanleyCupFinals;
    }

    public void setLastDayStanleyCupFinals(Date lastDayStanleyCupFinals) {
        this.lastDayStanleyCupFinals = lastDayStanleyCupFinals;
    }

    public boolean isTradeDeadlinePassed(Date currentDate){
        if(currentDate.compareTo(tradeDeadlineDate) > 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean isEndOfRegularSeason(Date currentDate){
        if(currentDate.compareTo(endOfRegularSeason) == 0){
            return true;
        }else{
            return false;
        }
    }
    public boolean isRegularSeasonPassed(Date currentDate){
        if(currentDate.compareTo(endOfRegularSeason) > 0){
            return true;
        }else{
            return false;
        }
    }
}
