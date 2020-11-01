package simulation.state;
import simulation.model.*;
import presentation.ConsoleOutput;
import util.DateUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class TrainingState implements ISimulateState, ITrainingState {

    private HockeyContext hockeyContext;
    private League league;
    private static final String TRAININGINFORMATION = "Training Players and Team!";
    private static final String STATCHECKINFORMATION = "Performing stat increase check";
    public TrainingState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
    }


    @Override
    public ISimulateState action() {
        ConsoleOutput.printToConsole(TRAININGINFORMATION);
        LocalDate currentDate = league.getCurrentDate();
        LocalDate seasonStartDate = league.getNHLRegularSeasonEvents().getRegularSeasonStartDate();
        Integer daysUntilStatIncreaseCheck  =league.getGamePlayConfig().getTraining().getDaysUntilStatIncreaseCheck();
        Long diffDays = DateUtil.diffDays(seasonStartDate,currentDate);
        if(diffDays % daysUntilStatIncreaseCheck == 1){
            ConsoleOutput.printToConsole(STATCHECKINFORMATION);
            statIncreaseCheck(league);
        }
        return exit();
    }

    @Override
    public void statIncreaseCheck(League league) {
        if(league==null){
            return;
        }
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
        if(player==null || headCoach ==null){
            return;
        }
        double coachShootingStrength = headCoach.getShooting();
        double coachSkatingStrength = headCoach.getSkating();
        double coachCheckingStrength = headCoach.getChecking();
        double coachSavingStrength = headCoach.getSaving();

        if (isRandomLess(coachShootingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getShooting() + 1)) {
                player.setShooting(player.getShooting() + 1);
            }
        } else {
            player.injuryCheck(league);
        }
        if (isRandomLess(coachSkatingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getSkating() + 1)) {
                player.setSkating(player.getSkating() + 1);
            }

        } else {
            player.injuryCheck(league);
        }
        if (isRandomLess(coachCheckingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getChecking() + 1)) {
                player.setChecking(player.getChecking() + 1);
            }

        } else {
            player.injuryCheck(league);
        }
        if (isRandomLess(coachSavingStrength)) {
            if (isStrengthInRangeAfterIncrease(player.getSaving() + 1)) {
                player.setSaving(player.getSaving() + 1);
            }
        } else {
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

    private boolean isRandomLess(double coachStrength) {
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
        List<Game> gamesOnCurrentDay = games.getUnPlayedGamesOnDate(league.getCurrentDate());
        if(gamesOnCurrentDay.size()== 0){
            if (nhlEvents.checkTradeDeadlinePassed(league.getCurrentDate())) {
                return new AgingState(hockeyContext);
            } else {
                return new ExecuteTradeState(hockeyContext);
            }
        }else{
            return new SimulateGameState(hockeyContext);
        }
    }
}
