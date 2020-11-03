package simulation.model;

import simulation.factory.ValidationConcrete;
import validator.IValidation;

import java.sql.Date;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class DateTime {

    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    public static LocalDate minusDays(LocalDate date, int days) {
        return date.minusDays(days);
    }

    public static long diffDays(LocalDate beforeDate, LocalDate afterDate) {
        return DAYS.between(beforeDate, afterDate);
    }

    public static Date convertLocalDateToSQLDate(LocalDate date) {
        ValidationConcrete validationConcrete = new ValidationConcrete();
        IValidation iValidation = validationConcrete.newValidation();

        if (iValidation.isNotNull(date)) {
            return java.sql.Date.valueOf(date);
        } else {
            return null;
        }
    }

}
