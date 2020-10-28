package simulation.state;

import simulation.RegularSeasonEvents.NHLEvents;
import simulation.model.*;
import userIO.ConsoleOutput;

import java.util.List;
import java.util.Random;

public class TrainingState implements ISimulateState, ITrainingState {

    private HockeyContext hockeyContext;
    private League league;

    public TrainingState(HockeyContext hockeyContext) {
        //Null check
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        ConsoleOutput.printToConsole("Training Players and Team!");
        return exit();
    }

    @Override
    public void statIncreaseCheck(League league) {
        List<Conference> conferenceList = league.getConferenceList();
        for (Conference conference : conferenceList) {
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                List<Team> teamList = division.getTeamList();
                for (Team team : teamList) {
                    List<Player> playerList = team.getPlayerList();
                    for (Player player : playerList) {
                        statIncreaseCheckForPlayer(player, team.getCoach());
                    }
                }
            }
        }
    }

    @Override
    public void statIncreaseCheckForPlayer(Player player, Coach headCoach) {

        double coachShootingStrength = headCoach.getShooting();
        double coachSkatingStrength = headCoach.getSkating();
        double coachCheckingStrength = headCoach.getChecking();
        double coachSavingStrength = headCoach.getSaving();

        if (isRandomLess(coachShootingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getShooting() + 1)) {
                player.setShooting(player.getShooting() + 1);
            }
        } else {
            // run injury check
            player.injuryCheck(league);
        }
        if (isRandomLess(coachSkatingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getSkating() + 1)) {
                player.setSkating(player.getSkating() + 1);
            }

        } else {
            // run injury check
            player.injuryCheck(league);
        }
        if (isRandomLess(coachCheckingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getChecking() + 1)) {
                player.setChecking(player.getChecking() + 1);
            }

        } else {
            // run injury check
            player.injuryCheck(league);
        }
        if (isRandomLess(coachSavingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getSaving() + 1)) {
                player.setSaving(player.getSaving() + 1);
            }
        } else {
            // run injury check
            player.injuryCheck(league);
        }
    }

    @Override
    public boolean isStrengthInRangeAfterIncrease(int strengthAfterIncrease) {
        if (strengthAfterIncrease >= 1 && strengthAfterIncrease <= 20) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isRandomLess(double coachStrength) {
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        if (randomNumber < coachStrength) {
            return true;
        } else {
            return false;
        }
    }

    public ISimulateState exit() {
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();

        Games games = league.getGames();
        List<Game> gamesOnCurrentDay = games.getUnplayedGamesOnDate(league.getCurrentDate());
        if (gamesOnCurrentDay.size() != 0) {
            return new SimulateGameState(hockeyContext);
        } else {
            if (nhlEvents.isTradeDeadlinePassed(league.getCurrentDate())) {
                return new AgingState(hockeyContext);
            } else {
                return new ExecuteTradeState(hockeyContext);
            }
        }
    }
}
