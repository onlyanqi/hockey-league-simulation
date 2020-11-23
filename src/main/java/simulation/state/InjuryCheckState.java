package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;

import java.util.List;

public class InjuryCheckState implements ISimulateState {

    public static final String INJURY_CHECK = "Injury Check!";
    private IHockeyContext hockeyContext;
    private League league;

    public InjuryCheckState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        ConsoleOutput.getInstance().printMsgToConsole(INJURY_CHECK);
        playerInjuryCheck(league);
        return exit();
    }

    private void playerInjuryCheck(League league) {
        Game game = league.getGames().getLastPlayedGame();
        Team team1 = league.getTeamByTeamName(game.getTeam1());
        Team team2 = league.getTeamByTeamName(game.getTeam2());
        List<Player> activePlayerList1 = team1.getActivePlayerList();
        List<Player> inactivePlayerList1 = team1.getInactivePlayerList();
        List<Player> activePlayerList2 = team2.getActivePlayerList();
        List<Player> inactivePlayerList2 = team2.getInactivePlayerList();
        int size1 = activePlayerList1.size();
        for (int i = size1 - 1; i >= 0; i--) {
            Player teamPlayer = activePlayerList1.get(i);
            teamPlayer.injuryCheck(league);
            if (teamPlayer.getInjured()) {
                teamPlayer.findBestReplacement(activePlayerList1, inactivePlayerList1);
                inactivePlayerList1.add(teamPlayer);
            }
        }
        int size2 = activePlayerList2.size();
        for (int i = size2 - 1; i >= 0; i--) {
            Player teamPlayer = activePlayerList2.get(i);
            teamPlayer.injuryCheck(league);
            if (teamPlayer.getInjured()) {
                teamPlayer.findBestReplacement(activePlayerList2, inactivePlayerList2);
                inactivePlayerList2.add(teamPlayer);
            }
        }
    }

    private ISimulateState exit() {
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();

        GameSchedule games = league.getGames();
        List<Game> gamesOnCurrentDay = games.getUnPlayedGamesOnDate(league.getCurrentDate());
        if (gamesOnCurrentDay.size() == 0) {
            if (nhlEvents.checkTradeDeadlinePassed(league.getCurrentDate())) {
                return new AgingState(hockeyContext);
            } else {
                return new ExecuteTradeState(hockeyContext);
            }
        } else {
            return new SimulateGameState(hockeyContext);
        }
    }

}
