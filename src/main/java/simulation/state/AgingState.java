package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.Games;
import simulation.model.League;
import simulation.model.TeamStanding;

import simulation.model.*;

import java.util.Comparator;
import java.util.List;

public class AgingState implements ISimulateState {

    private HockeyContext hockeyContext;
    private League league;

    public AgingState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        System.out.println("Aging players!");
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
                            Player.Position position = teamPlayer.getPosition();
                            playerList.remove(i);
                            this.findReplacement(playerList, position, i);
                            /*
                            save historical player stats (retired);
                             */
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

    private ISimulateState exit() {
        if(stanleyCupWinnerDetermined()){
            return new AdvanceNextSeasonState(hockeyContext);
        } else {
            return new PersistState(hockeyContext);
        }
    }

    public Boolean stanleyCupWinnerDetermined(){
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        Games games = league.getGames();
        TeamStanding teamStanding = league.getActiveTeamStanding();
        if(nhlEvents.isRegularSeasonPassed(league.getCurrentDate()) && games.doGamesDoesNotExistAfterDate(league.getCurrentDate()) && teamStanding.getTeamsScoreList().size() == 2 ){
            return true;
        }
        return false;
    }
}
