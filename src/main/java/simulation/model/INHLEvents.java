package simulation.model;

import java.time.LocalDate;


public interface INHLEvents {

    int getId();

    void setId(int id);

    LocalDate getNextSeasonDate();

    void setNextSeasonDate(LocalDate nextSeasonDate);

    LocalDate getRegularSeasonStartDate();

    void setRegularSeasonStartDate(LocalDate regularSeasonStartDate);

    LocalDate getEndOfRegularSeason();

    void setEndOfRegularSeason(LocalDate endOfRegularSeason);

    LocalDate getPlayOffStartDate();

    void setPlayOffStartDate(LocalDate playOffStartDate);

    LocalDate getTradeDeadlineDate();

    void setTradeDeadlineDate(LocalDate tradeDeadlineDate);

    LocalDate getLastDayStanleyCupFinals();

    void setLastDayStanleyCupFinals(LocalDate lastDayStanleyCupFinals);

    boolean checkTradeDeadlinePassed(LocalDate currentDate);

    boolean checkEndOfRegularSeason(LocalDate currentDate);

    boolean checkRegularSeasonPassed(LocalDate currentDate);

    void initializeRegularSeasonStartDate();

    void initializeTradeDeadlineDate();

    void initializeEndOfRegularSeason();

    void initializePlayOffStartDate();

    void initializeLastDayStanleyCupFinals();

    void initializeNextSeasonDate();

}
