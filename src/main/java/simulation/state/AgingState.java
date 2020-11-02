package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.NHLEvents;
import simulation.model.League;
import simulation.model.TeamStanding;

import simulation.model.*;

import java.util.List;

public class AgingState implements ISimulateState {

    public static final String AGING_DAY = "Aging all players by one day!";
    private HockeyContext hockeyContext;
    private League league;

    public AgingState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        ConsoleOutput.getInstance().printMsgToConsole(AGING_DAY);
        agingPlayerDay(league);
        return exit();
    }

    private void agingPlayerDay(League league) {
        List<Conference> conferenceList = league.getConferenceList();
        List<Player> freeAgentList = league.getFreeAgent().getPlayerList();
        for (Conference conference : conferenceList) {
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                List<Team> teamList = division.getTeamList();
                for (Team team : teamList) {
                    List<Player> playerList = team.getPlayerList();
                    for (Player teamPlayer : playerList) {
                        teamPlayer.agingInjuryRecovery(league);
                    }
                }
            }
        }
        for (Player freeAgentPlayer : freeAgentList) {
            freeAgentPlayer.agingInjuryRecovery(league);
        }
    }

    private ISimulateState exit() {
        if (stanleyCupWinnerDetermined()) {
            return new AdvanceNextSeasonState(hockeyContext);
        } else {
            return new PersistState(hockeyContext);
        }
    }

    public Boolean stanleyCupWinnerDetermined() {
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        GameSchedule games = league.getGames();
        TeamStanding teamStanding = league.getActiveTeamStanding();
        if (nhlEvents.checkRegularSeasonPassed(league.getCurrentDate()) && games.doGamesDoesNotExistAfterDate(league.getCurrentDate()) && teamStanding.getTeamsScoreList().size() == 2) {
            return true;
        }
        return false;
    }
}
