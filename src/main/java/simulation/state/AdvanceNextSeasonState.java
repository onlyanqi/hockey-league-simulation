package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.NHLEvents;
import simulation.model.*;
import java.util.Comparator;
import java.util.List;

public class AdvanceNextSeasonState implements ISimulateState {

    public static final String SEASON_CURRENT_DATE = "Advanced to next season! Current date is ";
    public static final String AGING_TO_NEXT_SEASON = "Aging all players to the start of next season!";
    private League league;
    private HockeyContext hockeyContext;
    private NHLEvents nhlEvents;

    public AdvanceNextSeasonState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.nhlEvents = league.getNHLRegularSeasonEvents();
    }

    @Override
    public ISimulateState action() {
        league.setCurrentDate(nhlEvents.getNextSeasonDate());

        ConsoleOutput.getInstance().printMsgToConsole(SEASON_CURRENT_DATE + nhlEvents.getNextSeasonDate());
        agingPlayerSeason(league);
        ConsoleOutput.getInstance().printMsgToConsole(AGING_TO_NEXT_SEASON);

        return exit();
    }

    private void agingPlayerSeason(League league) {
        List<Conference> conferenceList = league.getConferenceList();
        List<Player> freeAgentList = league.getFreeAgent().getPlayerList();
        Aging aging = league.getGamePlayConfig().getAging();

        for (Conference conference : conferenceList) {
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                List<Team> teamList = division.getTeamList();
                for (Team team : teamList) {
                    List<Player> playerList = team.getPlayerList();
                    for (int i = playerList.size() - 1; i >= 0; i--) {
                        Player teamPlayer = playerList.get(i);
                        teamPlayer.getOlder();
                        if (teamPlayer.retirementCheck(aging)) {
                            teamPlayer.setRetired(true);
                            Player.Position position = teamPlayer.getPosition();
                            playerList.remove(i);
                            this.findReplacement(playerList, position, i);
                        }
                        teamPlayer.agingInjuryRecovery(league);
                    }
                }
            }
        }

        for (Player freeAgentPlayer : freeAgentList) {
            for (int i = freeAgentList.size() - 1; i >= 0; i--) {
                freeAgentPlayer.getOlder();
                if (freeAgentPlayer.retirementCheck(aging)) {
                    freeAgentPlayer.setRetired(true);
                    freeAgentList.remove(i);
                }
                freeAgentPlayer.agingInjuryRecovery(league);
            }
        }
    }

    private void findReplacement(List<Player> playerList, Player.Position position, int index) {
        List<Player> freeAgentList = league.getFreeAgent().getPlayerList();

        freeAgentList.sort(new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                return Double.compare(p2.getStrength(), p1.getStrength());
            }
        });
        Player replacePlayer = new Player();
        for (int i = 0; i < freeAgentList.size(); i++) {
            if (freeAgentList.get(i).getPosition().equals(position)) {
                replacePlayer = new Player(freeAgentList.get(i));
                freeAgentList.remove(i);
                break;
            }
        }
        playerList.add(index, replacePlayer);
    }

    public ISimulateState exit() {
        return new PersistState(hockeyContext);
    }
}
