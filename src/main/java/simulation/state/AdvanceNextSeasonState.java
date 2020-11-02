package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.NHLEvents;
import simulation.model.*;

import java.util.*;

public class AdvanceNextSeasonState implements ISimulateState {

    public static final String SEASON_CURRENT_DATE = "Advanced to next season! Current date is ";
    public static final String AGING_TO_NEXT_SEASON = "Aging all players to the start of next season!";
    private League league;
    private HockeyContext hockeyContext;

    public AdvanceNextSeasonState(HockeyContext hockeyContext) {
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
        try {
            List<Conference> conferenceList = league.getConferenceList();
            List<Player> freeAgentList = league.getFreeAgent().getPlayerList();
            List<Player> retiredPlayerList = league.getRetiredPlayerList();
            Aging aging = league.getGamePlayConfig().getAging();

            for (Conference conference : conferenceList) {
                List<Division> divisionList = conference.getDivisionList();
                for (Division division : divisionList) {
                    List<Team> teamList = division.getTeamList();
                    for (Team team : teamList) {
                        List<Player> playerList = team.getPlayerList();
                        int size = playerList.size();
                        for (int i = size - 1; i >= 0; i--) {
                            Player teamPlayer = playerList.get(i);
                            teamPlayer.getOlder();
                            if (teamPlayer.retirementCheck(aging)) {
                                teamPlayer.setRetired(true);
                                retiredPlayerList.add(teamPlayer);
                                Player.Position position = teamPlayer.getPosition();
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
                Player freeAgentPlayer = freeAgentList.get(i);
                freeAgentPlayer.getOlder();
                if (freeAgentPlayer.retirementCheck(aging)) {
                    freeAgentPlayer.setRetired(true);
                    freeAgentList.remove(i);
                }
                freeAgentPlayer.agingInjuryRecovery(league);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findReplacement(List<Player> playerList, Player.Position position, int index) {
        List<Player> freeAgentList = league.getFreeAgent().getPlayerList();
        Collections.sort(freeAgentList, Collections.reverseOrder());
        Player replacePlayer = new Player();
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
