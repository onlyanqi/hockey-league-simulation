package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import simulation.model.DateTime;
import simulation.model.ILeague;
import simulation.model.INHLEvents;
import simulation.model.TeamStat;

import java.time.LocalDate;
import java.util.ArrayList;

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
        if (league.getCurrentDate() == null) {
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
        if (nhlEvents == null) {
            log.error("NHL Events is not set to league.Unable to proceed to further states.");
            throw new IllegalStateException("NHL Events is not set to league.Unable to proceed to further states.");
        }
        if (nhlEvents.checkEndOfRegularSeason(league.getCurrentDate())
                || nhlEvents.checkRegularSeasonPassed(league.getCurrentDate())) {
            if (nhlEvents.checkEndOfRegularSeason(league.getCurrentDate())) {
                displayTeamStats();
            }
            return new GeneratePlayoffScheduleState(hockeyContext);
        } else {
            return new TrainingState(hockeyContext);
        }
    }

    private void displayTeamStats() {
        ArrayList<TeamStat> teamStats = league.getTeamStats();
        float goalAvg = 0;
        float saveAvg = 0;
        float shotAvg = 0;
        float penaltyAvg = 0;
        for (TeamStat teamStat : teamStats) {
            goalAvg = goalAvg + (float) teamStat.getGoals() / teamStat.getNumberOfGamesPlayed();
            penaltyAvg = penaltyAvg + (float) teamStat.getPenalties() / teamStat.getNumberOfGamesPlayed();
            shotAvg = shotAvg + (float) teamStat.getShots() / teamStat.getNumberOfGamesPlayed();
            saveAvg = saveAvg + (float) teamStat.getSaves() / teamStat.getNumberOfGamesPlayed();
        }
        goalAvg = goalAvg / teamStats.size();
        penaltyAvg = penaltyAvg / teamStats.size();
        shotAvg = shotAvg / teamStats.size();
        saveAvg = saveAvg / teamStats.size();
        ConsoleOutput.getInstance().printGameStatsToUser(goalAvg, penaltyAvg, shotAvg, saveAvg);
    }
}
