package simulation.state;

import simulation.model.Game;
import simulation.model.League;
import simulation.model.Team;
import simulation.model.TeamStanding;
import java.util.List;

public class SimulateGameState implements ISimulateState {

    private IHockeyContext hockeyContext;
    private League league;

    public SimulateGameState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        List<Game> gamesOnCurrentDay = league.getGames().getUnPlayedGamesOnDate(league.getCurrentDate());
        Game game = gamesOnCurrentDay.get(0);

        simulateGame(game);
        updateTeamStandings(game);

        return exit();
    }

    public ISimulateState exit() {
        return new InjuryCheckState(hockeyContext);
    }

    public void simulateGame(Game game) {
        double upset = 0.2;
        Team team1 = league.getTeamByTeamName(game.getTeam1());
        Team team2 = league.getTeamByTeamName(game.getTeam2());

        if (team1.getStrength() > team2.getStrength()) {
            game.setWinner(Game.Result.TEAM1);
            team2.setLossPoint(team2.getLossPoint() + 1);
        } else {
            game.setWinner(Game.Result.TEAM2);
            team1.setLossPoint(team1.getLossPoint() + 1);
        }
        if (Math.random() <= upset) {
            if (game.getWinner().equals(Game.Result.TEAM1)) {
                game.setWinner(Game.Result.TEAM2);
                team1.setLossPoint(team1.getLossPoint() + 1);
            } else {
                game.setWinner(Game.Result.TEAM1);
                team2.setLossPoint(team2.getLossPoint() + 1);
            }
        }
        game.setPlayed(true);
    }

    public void updateTeamStandings(Game game) {
        TeamStanding teamStanding = league.getActiveTeamStanding();

        if (game.getWinner().equals(Game.Result.TEAM1)) {
            teamStanding.setTeamPoints(game.getTeam1());
            teamStanding.setTeamWins(game.getTeam1());
            teamStanding.setTeamLoss(game.getTeam2());
        } else if (game.getWinner().equals(Game.Result.TEAM2)) {
            teamStanding.setTeamPoints(game.getTeam2());
            teamStanding.setTeamWins(game.getTeam2());
            teamStanding.setTeamLoss(game.getTeam1());
        } else {
            teamStanding.setTeamTies(game.getTeam2());
            teamStanding.setTeamTies(game.getTeam1());
        }
    }
}
