package simulation.mock;

import persistance.dao.IEventDao;
import simulation.model.INHLEvents;

import java.time.LocalDate;
import java.time.Month;

public class NHLEventMock implements IEventDao {

    @Override
    public long addEvent(int leagueId, INHLEvents event) {
        event.setId(1);
        return event.getId();
    }

    @Override
    public void loadEventById(int id, INHLEvents event) {
        switch (id) {
            case 0:
                event.setId(0);
                event.setRegularSeasonStartDate(LocalDate.of(2020, Month.SEPTEMBER, 30));
                event.setTradeDeadlineDate(LocalDate.of(2021, Month.FEBRUARY, 03));
                event.setNextSeasonDate(LocalDate.of(2021, Month.SEPTEMBER, 29));
                event.setLastDayStanleyCupFinals(LocalDate.of(2021, Month.JUNE, 01));
                event.setPlayOffStartDate(LocalDate.of(2021, Month.APRIL, 05));
                event.setEndOfRegularSeason(LocalDate.of(2021, Month.APRIL, 06));
                break;
            case 1:
                event.setId(1);
                event.setRegularSeasonStartDate(LocalDate.of(2020, Month.SEPTEMBER, 30));
                event.setTradeDeadlineDate(LocalDate.of(2020, Month.FEBRUARY, 03));
                event.setNextSeasonDate(LocalDate.of(2021, Month.OCTOBER, 01));
                event.setLastDayStanleyCupFinals(LocalDate.of(2020, Month.JUNE, 01));
                event.setPlayOffStartDate(LocalDate.of(2021, Month.APRIL, 05));
                break;
            case 2:
                event.setId(2);
                event.setRegularSeasonStartDate(LocalDate.of(2020, Month.SEPTEMBER, 30));
                event.setTradeDeadlineDate(LocalDate.of(2021, Month.FEBRUARY, 03));
                event.setNextSeasonDate(LocalDate.of(2021, Month.OCTOBER, 01));
                event.setLastDayStanleyCupFinals(LocalDate.of(2021, Month.JUNE, 01));
                event.setPlayOffStartDate(LocalDate.of(2021, Month.APRIL, 05));
                event.setEndOfRegularSeason(null);
                break;
        }
    }

    @Override
    public void loadEventByLeagueId(int leagueId, INHLEvents event) {
        switch (leagueId) {
            case 0:
                event.setId(3);
                event.setRegularSeasonStartDate(LocalDate.of(2020, Month.SEPTEMBER, 30));
                event.setTradeDeadlineDate(LocalDate.of(2021, Month.FEBRUARY, 03));
                event.setNextSeasonDate(LocalDate.of(2021, Month.OCTOBER, 01));
                event.setLastDayStanleyCupFinals(LocalDate.of(2021, Month.JUNE, 01));
                event.setPlayOffStartDate(LocalDate.of(2021, Month.APRIL, 05));
                event.setEndOfRegularSeason(LocalDate.of(2021, Month.APRIL, 06));
                break;
            case 1:
                event.setId(4);
                event.setRegularSeasonStartDate(LocalDate.of(2020, Month.SEPTEMBER, 30));
                event.setTradeDeadlineDate(LocalDate.of(2020, Month.FEBRUARY, 03));
                event.setNextSeasonDate(LocalDate.of(2021, Month.OCTOBER, 01));
                event.setLastDayStanleyCupFinals(LocalDate.of(2020, Month.JUNE, 01));
                event.setPlayOffStartDate(LocalDate.of(2021, Month.APRIL, 05));
                break;
            case 2:
                event.setId(5);
                event.setRegularSeasonStartDate(LocalDate.of(2020, Month.SEPTEMBER, 30));
                event.setTradeDeadlineDate(LocalDate.of(2021, Month.FEBRUARY, 03));
                event.setNextSeasonDate(LocalDate.of(2021, Month.OCTOBER, 01));
                event.setLastDayStanleyCupFinals(LocalDate.of(2021, Month.JUNE, 01));
                event.setPlayOffStartDate(LocalDate.of(2021, Month.APRIL, 05));
                event.setEndOfRegularSeason(null);
                break;
        }
    }
}
