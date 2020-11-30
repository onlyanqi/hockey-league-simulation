package simulation.state;

import org.apache.log4j.Logger;
import simulation.GameSubjectObservers.*;
import simulation.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SimulateGameState implements ISimulateState {

    static Logger log = Logger.getLogger(SimulateGameState.class);
    private final IHockeyContext hockeyContext;
    private final ILeague league;

    public SimulateGameState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        List<IGame> gamesOnCurrentDay = league.getGames().getUnPlayedGamesOnDate(league.getCurrentDate());
        IGame game = gamesOnCurrentDay.get(0);
        try {
            simulateGame(game);
            updateTeamStandings(game);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return exit();
    }

    public ISimulateState exit() {
        return new InjuryCheckState(hockeyContext);
    }

    public void simulateGame(IGame game) throws Exception {
        ITeam team1 = league.getTeamByTeamName(game.getTeam1());
        ITeam team2 = league.getTeamByTeamName(game.getTeam2());
        log.debug("Started game between " + team1.getName() + " and " + team2.getName());
        Random rand = new Random();

        IGameSimulation gameSimulation = hockeyContext.getModelFactory().createGameSimulationFromTeams(team1, team2);
        gameSimulation.play();

        HashMap<String, Integer> goals = gameSimulation.getGoals();
        HashMap<String, Integer> shots = gameSimulation.getShots();
        HashMap<String, Integer> saves = gameSimulation.getSaves();
        HashMap<String, Integer> penalties = gameSimulation.getPenalties();

        notifyStats(team1, team2, goals, shots, saves, penalties);
        setWinner(game, team1, team2, rand, goals);

        game.setPlayed(true);
    }

    private void setWinner(IGame game, ITeam team1, ITeam team2, Random rand, HashMap<String, Integer> goals) {
        if (game == null) {
            log.error("game is null, unable to set winner");
            throw new IllegalArgumentException("game is null, unable to set winner");
        }
        if (team1 == null || team2 == null) {
            log.error("One of the teams are null, unable to set winner");
            throw new IllegalArgumentException("One of the teams are null, unable to set winner");
        }
        if (goals.get(team1.getName()) > goals.get(team2.getName())) {
            game.setWinner(Result.TEAM1);
            log.debug(team1.getName() + "won the game");
            team2.setLossPoint(team2.getLossPoint() + 1);
        } else if (goals.get(team1.getName()) < goals.get(team2.getName())) {
            log.debug(team2.getName() + "won the game");
            game.setWinner(Result.TEAM2);
            team1.setLossPoint(team1.getLossPoint() + 1);
        } else if (goals.get(team1.getName()) == goals.get(team2.getName())) {
            if (rand.nextDouble() > 0.5) {
                log.debug(team1.getName() + "won the game");
                game.setWinner(Result.TEAM1);
                team2.setLossPoint(team2.getLossPoint() + 1);
            } else {
                log.debug(team2.getName() + "won the game");
                game.setWinner(Result.TEAM2);
                team1.setLossPoint(team1.getLossPoint() + 1);
            }
        }
    }

    private void notifyStats(ITeam team1, ITeam team2, HashMap<String, Integer> goals, HashMap<String, Integer> shots, HashMap<String, Integer> saves, HashMap<String, Integer> penalties) {
        GoalSubject.getInstance().notifyObservers(league, team1.getName(), goals.get(team1.getName()));
        GoalSubject.getInstance().notifyObservers(league, team2.getName(), goals.get(team2.getName()));

        PenaltySubject.getInstance().notifyObservers(league, team1.getName(), penalties.get(team1.getName()));
        PenaltySubject.getInstance().notifyObservers(league, team2.getName(), penalties.get(team2.getName()));

        ShotSubject.getInstance().notifyObservers(league, team1.getName(), shots.get(team1.getName()));
        ShotSubject.getInstance().notifyObservers(league, team2.getName(), shots.get(team2.getName()));

        SaveSubject.getInstance().notifyObservers(league, team1.getName(), saves.get(team1.getName()));
        SaveSubject.getInstance().notifyObservers(league, team2.getName(), saves.get(team2.getName()));

        TotalGamesSubject.getInstance().notifyObservers(league, team1.getName(), 0);
        TotalGamesSubject.getInstance().notifyObservers(league, team2.getName(), 0);
    }

    private void updateTeamStandings(IGame game) {
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
