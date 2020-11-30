package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import simulation.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvanceNextSeasonState implements ISimulateState {

    public static final String SEASON_CURRENT_DATE = "Advanced to next season! Current date is ";
    public static final String UNABLE_TO_PROCEED_TO_FURTHER_STATES = "Current date is not set to league. Unable to proceed to further states.";
    public static final String CLEAN_DRAFT_PICKS = "Cleaned draft picks in all teams.";
    public static final String AGING_ALL_PLAYERS_FROM = "Aging all players from ";
    public static final String TO_NEXT_SEASON = " to next season";
    private ILeague league;
    private IHockeyContext hockeyContext;
    private IAging aging;
    private LocalDate beforeDate;
    private static Logger log = Logger.getLogger(AdvanceNextSeasonState.class);

    public AdvanceNextSeasonState(IHockeyContext hockeyContext, LocalDate before) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.aging = league.getGamePlayConfig().getAging();
        this.beforeDate = before;
    }

    @Override
    public ISimulateState action() {
        if (league.getCurrentDate() == null) {
            log.error(UNABLE_TO_PROCEED_TO_FURTHER_STATES);
            throw new IllegalStateException(UNABLE_TO_PROCEED_TO_FURTHER_STATES);
        }
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        LocalDate currentDate = nhlEvents.getNextSeasonDate();
        league.setCurrentDate(currentDate);
        ConsoleOutput.getInstance().printMsgToConsole(SEASON_CURRENT_DATE + currentDate);
        log.debug(SEASON_CURRENT_DATE + currentDate);
        cleanDraftPicks();
        log.debug(CLEAN_DRAFT_PICKS);
        aging.agingPlayerPeriod(league, beforeDate);
        log.debug(AGING_ALL_PLAYERS_FROM + beforeDate + TO_NEXT_SEASON + currentDate);
        ConsoleOutput.getInstance().printMsgToConsole(AGING_ALL_PLAYERS_FROM + beforeDate + TO_NEXT_SEASON + currentDate);
        return exit();
    }


    private void cleanDraftPicks() {
        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    List<String> draftPicks = new ArrayList<>(Arrays.asList(null, null, null, null, null, null, null));
                    team.setDraftPicks(draftPicks);
                }
            }
        }
    }

    public ISimulateState exit() {
        return new PersistState(hockeyContext);
    }
}
