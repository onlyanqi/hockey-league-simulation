package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import presentation.IConsoleOutput;
import simulation.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgingState implements ISimulateState {

    public static final String AGING_DAY = "Aging all players by one day!";
    public static final String UNABLE_TO_PROCEED_TO_FURTHER_STATES = "Current date is not set to league. Unable to proceed to further states.";
    private static Logger log = Logger.getLogger(AgingState.class);
    private IHockeyContext hockeyContext;
    private ILeague league;
    private IAging aging;
    private IConsoleOutput consoleOutput;


    public AgingState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.aging = league.getGamePlayConfig().getAging();
        consoleOutput = ConsoleOutput.getInstance();
    }

    @Override
    public ISimulateState action() {
        if (league.getCurrentDate() == null) {
            log.error(UNABLE_TO_PROCEED_TO_FURTHER_STATES);
            throw new IllegalStateException(UNABLE_TO_PROCEED_TO_FURTHER_STATES);
        }
        ConsoleOutput.getInstance().printMsgToConsole(AGING_DAY);
        aging.agingPlayerDay(league);
        aging.agingPlayerRetirement(league);
        return exit();
    }


    private ISimulateState exit() {
        if (stanleyCupWinnerDetermined()) {
            updateTeamScoreList();
            displayTeamStats();
            displayWinsLoss();
            displayStanleyCupWinner();
            return new TrophySystem(hockeyContext);
        } else {
            return new PersistState(hockeyContext);
        }
    }

    private void displayStanleyCupWinner() {
        List<ITeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
        if (teamScoreList.get(0).getNumberOfWins() > teamScoreList.get(1).getNumberOfWins()) {
            ConsoleOutput.getInstance().printMsgToConsole(teamScoreList.get(0).getTeam().getName() + " won the stanley cup!");
        } else {
            ConsoleOutput.getInstance().printMsgToConsole(teamScoreList.get(1).getTeam().getName() + " won the stanley cup!");
        }
        consoleOutput.printMsgToConsole("----------------------------------------");
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
        consoleOutput.printGameStatsToUser(goalAvg, penaltyAvg, shotAvg, saveAvg);
    }

    private void displayWinsLoss() {
        ITeamStanding teamStanding = league.getRegularSeasonStanding();
        consoleOutput.printTeamGameScore(teamStanding);
    }

    private void updateTeamScoreList() {
        HashMap<ITeam, Integer> stanleyCupTeamStanding = league.getStanleyCupFinalsTeamScores();
        List<ITeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
        for (ITeamScore teamScore : teamScoreList) {
            stanleyCupTeamStanding.put(teamScore.getTeam(), stanleyCupTeamStanding.get(teamScore.getTeam()) + teamScore.getPoints());
        }
    }

    private Boolean stanleyCupWinnerDetermined() {
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        IGameSchedule games = league.getGames();
        ITeamStanding teamStanding = league.getActiveTeamStanding();
        if (nhlEvents.checkRegularSeasonPassed(league.getCurrentDate())
                && games.doGamesDoesNotExistAfterDate(league.getCurrentDate()) && teamStanding.getTeamsScoreList().size() == 2) {
            return true;
        }
        return false;
    }
}
