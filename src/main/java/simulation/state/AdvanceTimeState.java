package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.League;
import util.DateUtil;

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
        System.out.println("Advanced day!");
        Date advancedDate = DateUtil.addDays(league.getCurrentDate(),1);
        league.setCurrentDate(advancedDate);
        return exit();
    }

    private ISimulateState exit() {
        NHLEvents nhlEvents = league.getNhlRegularSeasonEvents();
        if(nhlEvents.isEndOfRegularSeason(league.getCurrentDate())){
            return new GeneratePlayoffScheduleState(hockeyContext);
        }else{
            return new TrainingState(hockeyContext);
        }
    }
}
