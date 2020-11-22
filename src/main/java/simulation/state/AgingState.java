package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;

import java.util.List;

public class AgingState implements ISimulateState {

    public static final String AGING_DAY = "Aging all players by one day!";
    private IHockeyContext hockeyContext;
    private ILeague league;

    public AgingState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        ConsoleOutput.getInstance().printMsgToConsole(AGING_DAY);
        agingPlayerDay(league);
        return exit();
    }

    private void agingPlayerDay(ILeague league) {
        List<IConference> conferenceList = league.getConferenceList();
        List<IPlayer> freeAgentList = league.getFreeAgent().getPlayerList();
        for (IConference conference : conferenceList) {
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam team : teamList) {
                    team.setActivePlayerList();
                    List<IPlayer> playerList = team.getPlayerList();
                    for (IPlayer teamPlayer : playerList) {
                        teamPlayer.calculateAge(league);
                        // statDecay
                        teamPlayer.agingInjuryRecovery(league);
                    }
                }
            }
        }
        for (IPlayer freeAgentPlayer : freeAgentList) {
            freeAgentPlayer.calculateAge(league);
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
