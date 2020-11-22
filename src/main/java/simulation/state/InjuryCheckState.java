package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;

import java.util.List;

public class InjuryCheckState implements ISimulateState {

    public static final String INJURY_CHECK = "Injury Check!";
    private IHockeyContext hockeyContext;
    private ILeague league;

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

    private void playerInjuryCheck(ILeague league) {
        IGame game = league.getGames().getLastPlayedGame();
        ITeam team1 = league.getTeamByTeamName(game.getTeam1());
        ITeam team2 = league.getTeamByTeamName(game.getTeam2());
        List<IPlayer> activePlayerList1 = team1.getActivePlayerList();
        List<IPlayer> inactivePlayerList1 = team1.getInactivePlayerList();
        List<IPlayer> activePlayerList2 = team2.getActivePlayerList();
        List<IPlayer> inactivePlayerList2 = team2.getInactivePlayerList();
        int size1 = activePlayerList1.size();
        for (int i = size1 - 1; i >= 0; i--) {
            IPlayer teamPlayer = activePlayerList1.get(i);
            teamPlayer.injuryCheck(league);
            if (teamPlayer.getInjured()) {
                teamPlayer.findBestReplacement(activePlayerList1, teamPlayer.getPosition(), i, inactivePlayerList1);
            }
        }
        int size2 = activePlayerList2.size();
        for (int i = size2 - 1; i >= 0; i--) {
            IPlayer teamPlayer = activePlayerList2.get(i);
            teamPlayer.injuryCheck(league);
            if (teamPlayer.getInjured()) {
                teamPlayer.findBestReplacement(activePlayerList2, teamPlayer.getPosition(), i, inactivePlayerList2);
            }
        }
    }

    private ISimulateState exit() {
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();

        IGameSchedule games = league.getGames();
        List<IGame> gamesOnCurrentDay = games.getUnPlayedGamesOnDate(league.getCurrentDate());
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
