package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.NHLEvents;
import simulation.model.*;

import java.util.List;

public class InjuryCheckState implements ISimulateState {

    public static final String INJURY_CHECK = "Injury Check!";
    private HockeyContext hockeyContext;
    private League league;

    public InjuryCheckState(HockeyContext hockeyContext) {
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
        List<Player> playerList1 = team1.getPlayerList();
        List<Player> playerList2 = team2.getPlayerList();
        for (Player teamPlayer : playerList1) {
            teamPlayer.injuryCheck(league);
        }
        for (Player teamPlayer : playerList2) {
            teamPlayer.injuryCheck(league);
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
