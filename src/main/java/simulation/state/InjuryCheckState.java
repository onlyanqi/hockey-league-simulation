package simulation.state;

import simulation.model.*;

import java.util.List;

public class InjuryCheckState implements ISimulateState {

    private HockeyContext hockeyContext;
    private League league;

    public InjuryCheckState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        System.out.println("Injury Check!");
        playerInjuryCheck(league);
        return exit();
    }

    private void playerInjuryCheck(League league) {
        List<Conference> conferenceList = league.getConferenceList();
        List<Player> freeAgentList = league.getFreeAgent().getPlayerList();
        for (Conference conference : conferenceList) {
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                List<Team> teamList = division.getTeamList();
                for (Team team : teamList) {
                    List<Player> playerList = team.getPlayerList();
                    for (Player teamPlayer : playerList) {
                        teamPlayer.injuryCheck(league);
                    }
                }
            }
        }
        for (Player freeAgentPlayer : freeAgentList) {
            freeAgentPlayer.injuryCheck(league);
        }
    }

    private ISimulateState exit() {
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();

        Games games = league.getGames();
        List<Game> gamesOnCurrentDay = games.getUnPlayedGamesOnDate(league.getCurrentDate());
        if (gamesOnCurrentDay.size() != 0) {
            return new SimulateGameState(hockeyContext);
        } else {
            if (nhlEvents.checkTradeDeadlinePassed(league.getCurrentDate())) {
                return new AgingState(hockeyContext);
            } else {
                return new ExecuteTradeState(hockeyContext);
            }
        }
    }

}
