package simulation.RegularSeasonEvents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class NHLRegularSeasonInitialize {
    public NHLEvents nhlEvents;
    public Calendar cal;

    public NHLRegularSeasonInitialize() {
        this.nhlEvents = new NHLEvents();
        initializeEndOfRegularSeason();
        initializeTradeDeadlineDate();
        initializeRegularSeasonStartDate();
        initializePlayOffStartDate();
        initializeLastDayStanleyCupFinals();
    }

    public void initializeRegularSeasonStartDate(){
        cal = Calendar.getInstance();
        cal.set(Calendar.MONTH,10);
        cal.set(Calendar.DATE,01);
        nhlEvents.setRegularSeasonStartDate(cal.getTime());
    }

    public void initializeTradeDeadlineDate(){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            nhlEvents.setTradeDeadlineDate(format.parse("2021-02-22"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void initializeEndOfRegularSeason(){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            nhlEvents.setTradeDeadlineDate(format.parse("2021-04-03"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void initializePlayOffStartDate(){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            nhlEvents.setTradeDeadlineDate(format.parse("2021-04-07"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void initializeLastDayStanleyCupFinals(){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            nhlEvents.setTradeDeadlineDate(format.parse("2021-06-01"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void initializeNextSeasonDate(){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            nhlEvents.setTradeDeadlineDate(format.parse("2021-09-29"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
