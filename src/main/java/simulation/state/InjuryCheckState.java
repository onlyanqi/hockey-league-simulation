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
        List<IPlayer> playerList1 = team1.getPlayerList();
        List<IPlayer> playerList2 = team2.getPlayerList();
        for (IPlayer teamPlayer : playerList1) {
            teamPlayer.injuryCheck(league);
        }
        for (IPlayer teamPlayer : playerList2) {
            teamPlayer.injuryCheck(league);
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
