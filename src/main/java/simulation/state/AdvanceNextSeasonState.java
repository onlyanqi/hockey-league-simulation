package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.NHLEvents;
import simulation.model.*;

import java.util.*;

public class AdvanceNextSeasonState implements ISimulateState {

    public static final String SEASON_CURRENT_DATE = "Advanced to next season! Current date is ";
    public static final String AGING_TO_NEXT_SEASON = "Aging all players to the start of next season!";
    private ILeague league;
    private IHockeyContext hockeyContext;

    public AdvanceNextSeasonState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {

        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        league.setCurrentDate(nhlEvents.getNextSeasonDate());
        ConsoleOutput.getInstance().printMsgToConsole(SEASON_CURRENT_DATE + nhlEvents.getNextSeasonDate());

        agingPlayerSeason(league);
        ConsoleOutput.getInstance().printMsgToConsole(AGING_TO_NEXT_SEASON);

        return exit();
    }

    private void agingPlayerSeason(ILeague league) {
        List<IConference> conferenceList = league.getConferenceList();
        List<IPlayer> freeAgentList = league.getFreeAgent().getPlayerList();
        List<IPlayer> retiredPlayerList = league.getRetiredPlayerList();

        for (IConference conference : conferenceList) {
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam team : teamList) {
                    List<IPlayer> playerList = team.getPlayerList();
                    int size = playerList.size();
                    for (int i = size - 1; i >= 0; i--) {
                        IPlayer teamPlayer = playerList.get(i);
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
            IPlayer freeAgentPlayer = freeAgentList.get(i);
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
