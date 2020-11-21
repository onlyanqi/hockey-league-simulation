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
        IAging aging = league.getGamePlayConfig().getAging();

        for (IConference conference : conferenceList) {
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam team : teamList) {
                    List<IPlayer> playerList = team.getPlayerList();
                    int size = playerList.size();
                    for (int i = size - 1; i >= 0; i--) {
                        IPlayer teamPlayer = playerList.get(i);
                        teamPlayer.getOlder();
                        if (teamPlayer.retirementCheck(aging)) {
                            teamPlayer.setRetired(true);
                            retiredPlayerList.add(teamPlayer);
                            Position position = teamPlayer.getPosition();
                            this.findReplacement(playerList, position, i);
                            playerList.remove(i);
                        }
                        teamPlayer.agingInjuryRecovery(league);
                    }
                }
            }
        }
        int size = freeAgentList.size();
        for (int i = size - 1; i >= 0; i--) {
            IPlayer freeAgentPlayer = freeAgentList.get(i);
            freeAgentPlayer.getOlder();
            if (freeAgentPlayer.retirementCheck(aging)) {
                freeAgentPlayer.setRetired(true);
                freeAgentList.remove(i);
            }
            freeAgentPlayer.agingInjuryRecovery(league);
        }
    }


    public void findReplacement(List<IPlayer> playerList, Position position, int index) {
        List<IPlayer> freeAgentList = league.getFreeAgent().getPlayerList();
        Collections.sort(freeAgentList, Collections.reverseOrder());
        IPlayer replacePlayer = new Player();
        int size = freeAgentList.size();
        for (int i = 0; i < size; i++) {
            if (freeAgentList.get(i).getPosition().equals(position)) {
                freeAgentList.get(i).setTeamId(playerList.get(index).getTeamId());
                replacePlayer = new Player(freeAgentList.get(i));
                freeAgentList.remove(i);
                break;
            }
        }
        playerList.add(replacePlayer);
    }

    private ISimulateState exit() {
        return new PersistState(hockeyContext);
    }
}
