package simulation.state;

import simulation.model.*;

import java.util.List;

public class SimulateGameState implements ISimulateState {

    private IHockeyContext hockeyContext;
    private ILeague league;

    public SimulateGameState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        List<IGame> gamesOnCurrentDay = league.getGames().getUnPlayedGamesOnDate(league.getCurrentDate());
        IGame game = gamesOnCurrentDay.get(0);

        simulateGame(game);
        updateTeamStandings(game);

        return exit();
    }

    public ISimulateState exit() {
        return new InjuryCheckState(hockeyContext);
    }

    public void simulateGame(IGame game) {
        double upset = 0.2;
        ITeam team1 = league.getTeamByTeamName(game.getTeam1());
        ITeam team2 = league.getTeamByTeamName(game.getTeam2());

        if (team1.getStrength() > team2.getStrength()) {
            game.setWinner(Result.TEAM1);
            team2.setLossPoint(team2.getLossPoint() + 1);
        } else {
            game.setWinner(Result.TEAM2);
            team1.setLossPoint(team1.getLossPoint() + 1);
        }
        if (Math.random() <= upset) {
            if (game.getWinner().equals(Result.TEAM1)) {
                game.setWinner(Result.TEAM2);
                team1.setLossPoint(team1.getLossPoint() + 1);
            } else {
                game.setWinner(Result.TEAM1);
                team2.setLossPoint(team2.getLossPoint() + 1);
            }
        }
        game.setPlayed(true);
    }

    public void updateTeamStandings(IGame game) {
        ITeamStanding teamStanding = league.getActiveTeamStanding();

        if (game.getWinner().equals(Result.TEAM1)) {
            teamStanding.setTeamPoints(game.getTeam1());
            teamStanding.setTeamWins(game.getTeam1());
            teamStanding.setTeamLoss(game.getTeam2());
        } else if (game.getWinner().equals(Result.TEAM2)) {
            teamStanding.setTeamPoints(game.getTeam2());
            teamStanding.setTeamWins(game.getTeam2());
            teamStanding.setTeamLoss(game.getTeam1());
        } else {
            teamStanding.setTeamTies(game.getTeam2());
            teamStanding.setTeamTies(game.getTeam1());
        }
    }
}
