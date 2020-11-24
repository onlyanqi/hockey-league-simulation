package simulation.state;

import presentation.ReadUserInput;
import simulation.GamePubSub.*;
import simulation.model.*;


import java.util.*;

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
        Random rand = new Random();

        GameSimulation gameSimulation = new GameSimulation(team1,team2);
        gameSimulation.play();



        HashMap<String,Integer> goals = gameSimulation.getGoals();
        HashMap<String,Integer> shots = gameSimulation.getShots();
        HashMap<String,Integer> saves = gameSimulation.getSaves();
        HashMap<String,Integer> penalties = gameSimulation.getPenalties();

        if (goals.get(team1.getName()) > goals.get(team2.getName())) {
            game.setWinner(Result.TEAM1);
            team2.setLossPoint(team2.getLossPoint() + 1);
        } else if (goals.get(team1.getName()) < goals.get(team2.getName())) {
            game.setWinner(Result.TEAM2);
            team1.setLossPoint(team1.getLossPoint() + 1);
        }else if(goals.get(team1.getName()) == goals.get(team2.getName())){
            if(rand.nextDouble() > 0.5){
                game.setWinner(Result.TEAM1);
                team2.setLossPoint(team2.getLossPoint() + 1);
            }else{
                game.setWinner(Result.TEAM2);
                team1.setLossPoint(team1.getLossPoint() + 1);
            }
        }

        game.setPlayed(true);


        GoalSubject.getInstance().notifyObservers(league,team1.getName(),goals.get(team1.getName()));
        GoalSubject.getInstance().notifyObservers(league,team2.getName(),goals.get(team2.getName()));

        PenaltySubject.getInstance().notifyObservers(league,team1.getName(),penalties.get(team1.getName()));
        PenaltySubject.getInstance().notifyObservers(league,team2.getName(),penalties.get(team2.getName()));

        ShotSubject.getInstance().notifyObservers(league,team1.getName(),shots.get(team1.getName()));
        ShotSubject.getInstance().notifyObservers(league,team2.getName(),shots.get(team2.getName()));

        SaveSubject.getInstance().notifyObservers(league,team1.getName(),saves.get(team1.getName()));
        SaveSubject.getInstance().notifyObservers(league,team2.getName(),saves.get(team2.getName()));

        TotalGamesSubject.getInstance().notifyObservers(league,team1.getName(),0);
        TotalGamesSubject.getInstance().notifyObservers(league,team2.getName(),0);



        System.out.println("Team 1 Goals " + goals.get(team1.getName()));
        System.out.println("Team 2 Goals " + goals.get(team2.getName()));
        System.out.println("-----------------------------------------");
        System.out.println("Team 1 Penalties " + penalties.get(team1.getName()));
        System.out.println("Team 2 Penalties " + penalties.get(team2.getName()));
        System.out.println("-----------------------------------------");
        System.out.println("Team 1 Saves " + saves.get(team1.getName()));
        System.out.println("Team 2 Saves " + saves.get(team2.getName()));
        System.out.println("-----------------------------------------");
        System.out.println("Team 1 Shots " + shots.get(team1.getName()));
        System.out.println("Team 2 Shots " + shots.get(team2.getName()));
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
