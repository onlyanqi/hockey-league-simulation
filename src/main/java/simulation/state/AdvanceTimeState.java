package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import simulation.model.*;
import java.time.LocalDate;

public class AdvanceTimeState implements ISimulateState {

    private static Logger log = Logger.getLogger(AdvanceTimeState.class);
    private ILeague league;
    private IHockeyContext hockeyContext;

    public AdvanceTimeState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        if(league.getCurrentDate() == null){
            log.error("Current date is not set to league.Unable to proceed to further states.");
            throw new IllegalStateException("Current date is not set to league.Unable to proceed to further states.");
        }
        LocalDate advancedDate = DateTime.addDays(league.getCurrentDate(), 1);
        league.setCurrentDate(advancedDate);
        log.debug("Advanced Day. Current date is " + advancedDate);
        ConsoleOutput.getInstance().printMsgToConsole("Advanced Day. Current date is " + advancedDate);
        return exit();
    }

    private ISimulateState exit() {
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        if(nhlEvents == null){
            log.error("NHL Events is not set to league.Unable to proceed to further states.");
            throw new IllegalStateException("NHL Events is not set to league.Unable to proceed to further states.");
        }
        if (nhlEvents.checkEndOfRegularSeason(league.getCurrentDate())
                || nhlEvents.checkRegularSeasonPassed(league.getCurrentDate())) {
            return new GeneratePlayoffScheduleState(hockeyContext);
        } else {
            return new TrainingState(hockeyContext);
        }
    }
}
