package simulation.state;

import presentation.ConsoleOutput;
import presentation.IConsoleOutput;
import simulation.model.*;
import simulation.trophyPublisherSubsribers.TrophySystemPublisher;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class TrainingState implements ISimulateState, ITrainingState {

    private static final String TRAININGINFORMATION = "Training Players and Team!";
    private static final String STATCHECKINFORMATION = "Performing stat increase check";
    private final IHockeyContext hockeyContext;
    private final ILeague league;
    private final IConsoleOutput consoleOutput;

    public TrainingState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
        consoleOutput = ConsoleOutput.getInstance();
    }

    @Override
    public ISimulateState action() {
        consoleOutput.printMsgToConsole(TRAININGINFORMATION);
        LocalDate currentDate = league.getCurrentDate();
        LocalDate seasonStartDate = league.getNHLRegularSeasonEvents().getRegularSeasonStartDate();
        Integer daysUntilStatIncreaseCheck = league.getGamePlayConfig().getTraining().getDaysUntilStatIncreaseCheck();
        Long diffDays = DateTime.diffDays(seasonStartDate, currentDate);
        if (daysUntilStatIncreaseCheck == 0) {
            System.out.println("Days Until Stat Increase Check is 0. So Exiting");
            return null;
        }
        if (diffDays > daysUntilStatIncreaseCheck && (diffDays % daysUntilStatIncreaseCheck == 1)) {
            consoleOutput.printMsgToConsole(STATCHECKINFORMATION);
            statIncreaseCheck(league);
        }
        return exit();
    }

    @Override
    public void statIncreaseCheck(ILeague league) {
        if (league == null) {
            return;
        }
        List<IConference> conferenceList = league.getConferenceList();
        for (IConference conference : conferenceList) {
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam team : teamList) {
                    List<IPlayer> activePlayerList = team.getActivePlayerList();
                    List<IPlayer> inactivePlayerList = team.getInactivePlayerList();
                    int size = activePlayerList.size();
                    for (int i = size - 1; i >= 0; i--) {
                        IPlayer activePlayer = activePlayerList.get(i);
                        statIncreaseCheckForPlayer(activePlayer, team.getCoach());
                        if (activePlayer.getInjured()) {
                            activePlayer.findBestReplacement(activePlayerList, inactivePlayerList);
                            inactivePlayerList.add(activePlayer);
                        }
                    }
                    for (IPlayer inactivePlayer : inactivePlayerList) {
                        statIncreaseCheckForPlayer(inactivePlayer, team.getCoach());
                    }
                }
            }
        }
    }

    @Override
    public void statIncreaseCheckForPlayer(IPlayer player, ICoach headCoach) {
        TrophySystemPublisher trophySystemPublisher = TrophySystemPublisher.getInstance();

        if (player == null || headCoach == null) {
            return;
        }
        double coachShootingStrength = headCoach.getShooting();
        double coachSkatingStrength = headCoach.getSkating();
        double coachCheckingStrength = headCoach.getChecking();
        double coachSavingStrength = headCoach.getSaving();

        if (isRandomLess(coachShootingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getShooting() + 1)) {
                player.setShooting(player.getShooting() + 1);
                trophySystemPublisher.notify("coachStatAbilityUpdate", headCoach, 1);
                consoleOutput.printMsgToConsole("Shooting strength of " + player.getName() + " was" + (player.getShooting() - 1) + " and increased to " + player.getShooting());
            }
        } else {
            player.injuryCheck(league);
        }
        if (isRandomLess(coachSkatingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getSkating() + 1)) {
                player.setSkating(player.getSkating() + 1);
                headCoach.setCoachingEffectiveness(1);
                trophySystemPublisher.notify("coachStatAbilityUpdate", headCoach, 1);
                consoleOutput.printMsgToConsole("Skating strength of " + player.getName() + " was" + (player.getSkating() - 1) + " and increased to " + player.getSkating());
            }

        } else {
            player.injuryCheck(league);
        }
        if (isRandomLess(coachCheckingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getChecking() + 1)) {
                player.setChecking(player.getChecking() + 1);
                headCoach.setCoachingEffectiveness(1);
                trophySystemPublisher.notify("coachStatAbilityUpdate", headCoach, 1);
                consoleOutput.printMsgToConsole("Checking strength of " + player.getName() + " was" + (player.getChecking() - 1) + " and increased to " + player.getChecking());
            }

        } else {
            player.injuryCheck(league);
        }
        if (isRandomLess(coachSavingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getSaving() + 1)) {
                player.setSaving(player.getSaving() + 1);
                headCoach.setCoachingEffectiveness(1);
                trophySystemPublisher.notify("coachStatAbilityUpdate", headCoach, 1);
                consoleOutput.printMsgToConsole("Saving strength of " + player.getName() + " was" + (player.getSaving() - 1) + " and increased to " + player.getSaving());
            }
        } else {
            player.injuryCheck(league);
        }
    }

    @Override
    public boolean isStrengthInRangeAfterIncrease(int strengthAfterIncrease) {
        return strengthAfterIncrease >= 1 && strengthAfterIncrease <= 20;
    }

    private boolean isRandomLess(double coachStrength) {
        Random rand = new Random();
        double randomNumber = rand.nextDouble();
        return randomNumber < coachStrength;
    }

    public ISimulateState exit() {
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
