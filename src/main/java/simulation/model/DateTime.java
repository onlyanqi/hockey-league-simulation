package simulation.model;

import java.time.LocalDate;
import java.sql.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateTime {

    public static LocalDate addDays(LocalDate date, int days)
    {
        return date.plusDays(days);
    }

    public static LocalDate minusDays(LocalDate date, int days)
    {
        return date.minusDays(days);
    }

    public static long diffDays(LocalDate beforeDate, LocalDate afterDate){
        return DAYS.between(beforeDate, afterDate);
    }

    public static Date convertLocalDateToSQLDate(LocalDate date){
        if(date != null){
            return java.sql.Date.valueOf(date);
        }else{
            return null;
        }
    }

}
