package util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateUtil{



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

}
