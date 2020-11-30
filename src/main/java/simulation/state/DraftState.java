package simulation.state;

import org.apache.log4j.Logger;
import presentation.ConsoleOutput;
import simulation.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DraftState implements ISimulateState {
    public static final String AGING_ALL_PLAYERS_FROM = "Aging all players from ";
    public static final String TO_NEXT_SEASON = " to next season";
    public static final String ADVANCED = "Advanced to the player draft state! Current date is ";
    public static final String SEVEN_ROUND_DRAFT = "Generated new players for the seven-round draft";
    public static final String NUMBER = "Fixed the number of team players back to 30.";
    public static final String RANKING = "Perform draft picks using the ranking in regularSeasonStanding and StanleyCupFinalsTeamStanding.";
    public static final String UNABLE_TO_PROCEED_TO_FURTHER_STATES = "Current date is not set to league. Unable to proceed to further states.";
    public static final String PICKED_DRAFTS_IN = "Picked drafts in ";
    public static final String ROUNDS = " rounds";
    public static final String REGULAR_SEASON_TEAM_STANDING_TEAM = "Using Regular Season Team Standing, team ";
    public static final String PICKING_A_DRAFT = " is picking a draft";
    public static final String STANLEY_CUP_FINALS_TEAM_STANDING_TEAM = "Using StanleyCup Finals Team Standing, team ";
    public static final String TEAM_STANDING_TEAMS_PICKED_DRAFTS = "Using Regular Season Team Standing, teams picked drafts";
    public static final String FINALS_TEAM_STANDING_TEAMS_PICKED_DRAFTS = "Using StanleyCup Finals Team Standing, teams picked drafts";
    public static final String SELECT_A_PICK = " select a pick ";
    public static final String ALREADY_TRADED_FROM = "already traded from ";
    public static final String TO = " to ";
    private static Logger log = Logger.getLogger(DraftState.class);
    List<String> firstNameList = Arrays.asList("Adam", "Alex", "Aaron", "Ben", "Carl", "Dan", "David", "Edward", "Fred", "Frank", "George", "Hal", "Hank", "Ike", "John", "Jack", "Joe", "Larry", "Monte", "Matthew", "Mark", "Nathan", "Otto", "Paul", "Peter", "Roger", "Roger", "Steve", "Thomas", "Tim", "Ty", "Victor", "Walter", "Walter", "John", "Prashant", "Hardik", "Rashita", "Mary", "Fred", "Mohammed", "Raghav", "Bonnie", "Tami", "Chris", "Pat", "Sammy", "Abraham", "Tina", "Nancy", "Roger", "Mike", "Rob", "Zongming", "Wen", "Don", "Rahul", "Sai", "Prabhjot", "Mozhgan", "Shakuntala", "Karan", "Jimmy");
    List<String> lastNameList = Arrays.asList("Anderson", "Ashwoon", "Aikin", "Bateman", "Bongard", "Bowers", "Boyd", "Cannon", "Cast", "Deitz", "Dewalt", "Ebner", "Frick", "Hancock", "Haworth", "Hesch", "Hoffman", "Kassing", "Knutson", "Lawless", "Lawicki", "Mccord", "McCormack", "Miller", "Myers", "Nugent", "Ortiz", "Orwig", "Ory", "Paiser", "Pak", "Pettigrew", "Quinn", "Quizoz", "Ramachandran", "Resnick", "Sagar", "Schickowski", "Schiebel", "Sellon", "Severson", "Shaffer", "Solberg", "Soloman", "Sonderling", "Soukup", "Soulis", "Stahl", "Sweeney", "Tandy", "Trebil", "Trusela", "Trussel", "Turco", "Uddin", "Uflan", "Ulrich", "Upson", "Vader", "Vail", "Valente", "Van Zandt", "Vanderpoel", "Ventotla", "Vogal", "Wagle", "Wagner", "Wakefield", "Weinstein", "Weiss", "Woo", "Yang", "Yates", "Yocum", "Zeaser", "Zeller", "Ziegler", "Bauer", "Baxster", "Casal", "Cataldi", "Caswell", "Celedon", "Chambers", "Chapman", "Christensen", "Darnell", "Davidson", "Davis", "DeLorenzo", "Dinkins", "Doran", "Dugelman", "Dugan", "Duffman", "Eastman", "Ferro", "Ferry", "Fletcher", "Fietzer", "Hylan", "Hydinger", "Illingsworth", "Ingram", "Irwin", "Jagtap", "Jenson", "Johnson", "Johnsen", "Jones", "Jurgenson", "Kalleg", "Kaskel", "Keller", "Leisinger", "LePage", "Lewis", "Linde", "Lulloff", "Maki", "Martin", "McGinnis", "Mills", "Moody", "Moore", "Napier", "Nelson", "Norquist", "Nuttle", "Olson", "Ostrander", "Reamer", "Reardon", "Reyes", "Rice", "Ripka", "Roberts", "Rogers", "Root", "Sandstrom", "Sawyer", "Schlicht", "Schmitt", "Schwager", "Schutz", "Schuster", "Tapia", "Thompson", "Tiernan", "Tisler");
    private final IHockeyContext hockeyContext;
    private final ILeague league;
    private final IAging aging;
    private final LocalDate beforeDate;

    public DraftState(IHockeyContext hockeyContext, LocalDate before) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.aging = league.getGamePlayConfig().getAging();
        this.beforeDate = before;
    }

    @Override
    public ISimulateState action() throws Exception {
        if (league.getCurrentDate() == null) {
            log.error(UNABLE_TO_PROCEED_TO_FURTHER_STATES);
            throw new IllegalStateException(UNABLE_TO_PROCEED_TO_FURTHER_STATES);
        }
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        LocalDate currentDate = nhlEvents.getPlayerDraftDate();
        league.setCurrentDate(currentDate);
        ConsoleOutput.getInstance().printMsgToConsole(ADVANCED + currentDate);
        log.debug(ADVANCED + currentDate);
        aging.agingPlayerPeriod(league, beforeDate);
        log.debug(AGING_ALL_PLAYERS_FROM + beforeDate + TO_NEXT_SEASON + currentDate);
        ConsoleOutput.getInstance().printMsgToConsole(AGING_ALL_PLAYERS_FROM + beforeDate + TO_NEXT_SEASON + currentDate);
        int totalRound = 7;
        ITeamStanding regularSeasonStanding = league.getRegularSeasonStanding();
        int teamNum = regularSeasonStanding.getTeamsRankAcrossLeague(league).size();
        generatePlayers(totalRound, teamNum);
        log.debug(SEVEN_ROUND_DRAFT);
        performDraft(totalRound, teamNum, regularSeasonStanding);
        log.debug(RANKING);
        fixAllTeamPlayerNum();
        log.debug(NUMBER);
        return exit();
    }

    public ISimulateState exit() throws Exception {
        return new AdvanceNextSeasonState(hockeyContext, league.getCurrentDate());
    }

    private void performDraft(int totalRound, int teamNum, ITeamStanding teamStanding) {
        int firstN = teamNum - 16;
        List<IPlayer> selectPlayerList = new ArrayList<>(league.getDraftedPlayerList());
        Collections.sort(selectPlayerList);
        Stack<IPlayer> playerStack = new Stack<>();
        while (selectPlayerList.size() > 0) {
            playerStack.push(selectPlayerList.remove(0));
        }
        int roundNum = 1;
        while (roundNum <= totalRound) {
            log.debug("Picked drafts in " + roundNum + " rounds");
            ConsoleOutput.getInstance().printMsgToConsole(PICKED_DRAFTS_IN + roundNum + ROUNDS);
            for (int i = firstN - 1; i >= 0; i--) {
                ITeam originalTeam = teamStanding.getTeamsRankAcrossLeague(league).get(i).getTeam();
                log.debug(REGULAR_SEASON_TEAM_STANDING_TEAM + originalTeam.getName() + PICKING_A_DRAFT);
                selectDraft(playerStack, roundNum, originalTeam);
            }
            ConsoleOutput.getInstance().printMsgToConsole(TEAM_STANDING_TEAMS_PICKED_DRAFTS);
            Set<ITeam> remainingTeams = league.getStanleyCupFinalsTeamScores().keySet();
            List<ITeam> secondTeamList = new ArrayList<>(remainingTeams);
            for (int j = 15; j >= 0; j--) {
                ITeam originalTeam = secondTeamList.get(j);
                log.debug(STANLEY_CUP_FINALS_TEAM_STANDING_TEAM + originalTeam.getName() + PICKING_A_DRAFT);
                selectDraft(playerStack, roundNum, originalTeam);
            }
            ConsoleOutput.getInstance().printMsgToConsole(FINALS_TEAM_STANDING_TEAMS_PICKED_DRAFTS);
            roundNum++;
        }
    }

    private void selectDraft(Stack<IPlayer> playerStack, int roundNum, ITeam originalTeam) {
        String tradedToTeamName = originalTeam.getDraftPicks().get(roundNum - 1);
        if (tradedToTeamName == null) {
            List<IPlayer> existingPlayers = originalTeam.getPlayerList();
            IPlayer player = playerStack.pop();
            player.setTeamId(originalTeam.getId());
            existingPlayers.add(player);
            originalTeam.setPlayerList(existingPlayers);
            log.debug(originalTeam.getName() + SELECT_A_PICK + player.getName());
        } else {
            ITeam tradedToTeam = league.getTeamByTeamName(tradedToTeamName);
            List<IPlayer> existingPlayers = tradedToTeam.getPlayerList();
            IPlayer player = playerStack.pop();
            player.setTeamId(tradedToTeam.getId());
            existingPlayers.add(player);
            tradedToTeam.setPlayerList(existingPlayers);
            log.debug("Draft " + player.getName() + ALREADY_TRADED_FROM + originalTeam.getName() + TO + tradedToTeamName);
            ConsoleOutput.printTradedDraftInfo(originalTeam, tradedToTeamName, player);
        }
    }

    private void fixAllTeamPlayerNum() {
        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    team.fixTeamPlayerNum(league.getFreeAgent().getPlayerList());
                    team.setActivePlayerList();
                }
            }
        }
    }

    public void generatePlayers(int round, int teamNum) {
        List<IPlayer> draftedPlayerList = new ArrayList<>();
        league.setDraftedPlayerList(draftedPlayerList);
        int playersNum = round * teamNum;
        int addedPlayers = 0;
        while (addedPlayers < playersNum) {
            IModelFactory playerFactory = hockeyContext.getModelFactory();
            IPlayer player = playerFactory.createPlayer();
            player.setName(getRandomName(firstNameList, lastNameList));
            player.setPosition(Position.generateRandomPosition());
            player.setBirthday(generateBirthday());
            player.calculateAge(league);
            player.setInjured(false);
            player.setCaptain(false);
            player.setIsFreeAgent(false);
            generateStats(player);
            player.setStrength();
            player.setRelativeStrength();
            draftedPlayerList.add(player);
            addedPlayers++;
        }
        league.setDraftedPlayerList(draftedPlayerList);
    }

    private void generateStats(IPlayer player) {
        Position position = player.getPosition();
        if (position == Position.FORWARD) {
            int skating = ThreadLocalRandom.current().nextInt(12, 21);
            player.setSkating(skating);
            int shooting = ThreadLocalRandom.current().nextInt(12, 21);
            player.setShooting(shooting);
            int checking = ThreadLocalRandom.current().nextInt(9, 19);
            player.setChecking(checking);
            int saving = ThreadLocalRandom.current().nextInt(1, 8);
            player.setSaving(saving);
        } else if (position == Position.DEFENSE) {
            int skating = ThreadLocalRandom.current().nextInt(10, 20);
            player.setSkating(skating);
            int shooting = ThreadLocalRandom.current().nextInt(9, 19);
            player.setShooting(shooting);
            int checking = ThreadLocalRandom.current().nextInt(12, 21);
            player.setChecking(checking);
            int saving = ThreadLocalRandom.current().nextInt(1, 13);
            player.setSaving(saving);
        } else if (position == Position.GOALIE) {
            int skating = ThreadLocalRandom.current().nextInt(8, 16);
            player.setSkating(skating);
            int shooting = ThreadLocalRandom.current().nextInt(1, 11);
            player.setShooting(shooting);
            int checking = ThreadLocalRandom.current().nextInt(1, 13);
            player.setChecking(checking);
            int saving = ThreadLocalRandom.current().nextInt(12, 21);
            player.setSaving(saving);
        }
    }

    private LocalDate generateBirthday() {
        LocalDate current = league.getCurrentDate();
        LocalDate start = LocalDate.of(current.getYear() - 21, current.getMonth(), current.getDayOfMonth());
        LocalDate end = LocalDate.of(current.getYear() - 18, current.getMonth(), current.getDayOfMonth());
        return between(start, end);
    }

    private LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
        long startEpochDay = startInclusive.toEpochDay();
        long endEpochDay = endExclusive.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    private String getRandomName(List<String> firstNameList, List<String> lastNameList) {
        if (firstNameList.size() == 0 || lastNameList.size() == 0) {
            return null;
        } else {
            String name = firstNameList.get(ThreadLocalRandom.current().nextInt(firstNameList.size())) + " " +
                    lastNameList.get(ThreadLocalRandom.current().nextInt(lastNameList.size()));
            return name;
        }
    }

}
