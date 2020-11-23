package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.NHLEvents;
import simulation.model.*;

import java.util.*;

public class AdvanceNextSeasonState implements ISimulateState {

    public static final String SEASON_CURRENT_DATE = "Advanced to next season! Current date is ";
    public static final String AGING_TO_NEXT_SEASON = "Aging all players to the start of next season!";
    private League league;
    private IHockeyContext hockeyContext;

    public AdvanceNextSeasonState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {

        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        league.setCurrentDate(nhlEvents.getNextSeasonDate());
        ConsoleOutput.getInstance().printMsgToConsole(SEASON_CURRENT_DATE + nhlEvents.getNextSeasonDate());

        agingPlayerSeason(league);
        ConsoleOutput.getInstance().printMsgToConsole(AGING_TO_NEXT_SEASON);

        return exit();
    }

    private void agingPlayerSeason(League league) {
        List<Conference> conferenceList = league.getConferenceList();
        List<Player> freeAgentList = league.getFreeAgent().getPlayerList();
        List<Player> retiredPlayerList = league.getRetiredPlayerList();

        for (Conference conference : conferenceList) {
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                List<Team> teamList = division.getTeamList();
                for (Team team : teamList) {
                    List<Player> playerList = team.getPlayerList();
                    int size = playerList.size();
                    for (int i = size - 1; i >= 0; i--) {
                        Player teamPlayer = playerList.get(i);
                        if (teamPlayer.retirementCheck(league)) {
                            teamPlayer.setRetired(true);
                            retiredPlayerList.add(teamPlayer);
                            playerList.remove(teamPlayer);
                            teamPlayer.findBestReplacement(playerList, freeAgentList);
                        }
                        teamPlayer.agingInjuryRecovery(league);
                    }
                }
            }
        }
        int size = freeAgentList.size();
        for (int i = size - 1; i >= 0; i--) {
            Player freeAgentPlayer = freeAgentList.get(i);
            if (freeAgentPlayer.retirementCheck(league)) {
                freeAgentPlayer.setRetired(true);
                retiredPlayerList.add(freeAgentPlayer);
                freeAgentList.remove(i);
            }
            freeAgentPlayer.agingInjuryRecovery(league);
        }
    }

    private ISimulateState exit() {
        return new PersistState(hockeyContext);
    }
}
