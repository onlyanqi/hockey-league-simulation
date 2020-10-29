package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.League;
import util.DateUtil;

import java.time.LocalDate;
import java.util.Date;

public class AdvanceTimeState implements ISimulateState {

    private League league;
    private HockeyContext hockeyContext;

    public AdvanceTimeState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        LocalDate advancedDate = DateUtil.addDays(league.getCurrentDate(),1);
        league.setCurrentDate(advancedDate);
        System.out.println("Advanced day! Current date is" + advancedDate);
        return exit();
    }

    private ISimulateState exit() {
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        if(nhlEvents.isEndOfRegularSeason(league.getCurrentDate()) || nhlEvents.isRegularSeasonPassed(league.getCurrentDate())){
            return new GeneratePlayoffScheduleState(hockeyContext);
        }else{
            return new TrainingState(hockeyContext);
        }
    }
}
