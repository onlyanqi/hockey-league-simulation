package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DraftState implements ISimulateState {
    private IHockeyContext hockeyContext;
    private ILeague league;
    private IAging aging;
    private LocalDate beforeDate;

    public DraftState(IHockeyContext hockeyContext, LocalDate before) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.aging = league.getGamePlayConfig().getAging();
        this.beforeDate = before;
    }

    @Override
    public ISimulateState action() throws Exception {
        INHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        league.setCurrentDate(nhlEvents.getPlayerDraftDate());
        ConsoleOutput.getInstance().printMsgToConsole("Advanced to the player draft state! Current date is " + nhlEvents.getPlayerDraftDate());
        aging.agingPlayerPeriod(league, beforeDate);
        int totalRound = 7;
        ITeamStanding regularSeasonStanding = league.getRegularSeasonStanding();
        int teamNum = regularSeasonStanding.getTeamsRankAcrossLeague(league).size();
        generatePlayers(totalRound, teamNum);
        performDraft(totalRound, teamNum, regularSeasonStanding);
        fixAllTeamPlayerNum();
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
            ConsoleOutput.getInstance().printMsgToConsole("Picked drafts in " + roundNum + " rounds");
            for (int i = firstN - 1; i >= 0; i--) {
                ITeam originalTeam = teamStanding.getTeamsRankAcrossLeague(league).get(i).getTeam();
                String tradedToTeamName = originalTeam.getDraftPicks().get(roundNum - 1);
                if (tradedToTeamName == null) {
                    List<IPlayer> existingPlayers = originalTeam.getPlayerList();
                    IPlayer player = playerStack.pop();
                    player.setTeamId(originalTeam.getId());
                    existingPlayers.add(player);
                    originalTeam.setPlayerList(existingPlayers);
                } else{
                    ITeam tradedToTeam = league.getTeamByTeamName(tradedToTeamName);
                    List<IPlayer> existingPlayers = tradedToTeam.getPlayerList();
                    IPlayer player = playerStack.pop();
                    player.setTeamId(tradedToTeam.getId());
                    existingPlayers.add(player);
                    tradedToTeam.setPlayerList(existingPlayers);
                }

            }
            Set<ITeam> remainingTeams = league.getStanleyCupFinalsTeamScores().keySet();
            List<ITeam> secondTeamList = new ArrayList<>(remainingTeams);
            for (int j = 15; j >= 0; j--) {
                ITeam originalTeam = secondTeamList.get(j);
                String tradedToTeamName = originalTeam.getDraftPicks().get(roundNum - 1);
                if(tradedToTeamName == null){
                    List<IPlayer> existingPlayers = originalTeam.getPlayerList();
                    IPlayer player = playerStack.pop();
                    player.setTeamId(originalTeam.getId());
                    existingPlayers.add(player);
                    originalTeam.setPlayerList(existingPlayers);
                }else {
                    ITeam tradedToTeam = league.getTeamByTeamName(tradedToTeamName);
                    List<IPlayer> existingPlayers = tradedToTeam.getPlayerList();
                    IPlayer player = playerStack.pop();
                    player.setTeamId(tradedToTeam.getId());
                    existingPlayers.add(player);
                    tradedToTeam.setPlayerList(existingPlayers);
                }
            }
            roundNum++;
        }
    }

    private void fixAllTeamPlayerNum(){
        for (IConference conference : league.getConferenceList()) {
            for (IDivision division : conference.getDivisionList()) {
                for (ITeam team : division.getTeamList()) {
                    team.fixTeamPlayerNum(league.getFreeAgent().getPlayerList());
                    team.setActivePlayerList();
                }
            }
        }
    }

    private void generatePlayers(int round, int teamNum) {
        List<IPlayer> draftedPlayerList = new ArrayList<>();
        league.setDraftedPlayerList(draftedPlayerList);
        int playersNum = round * teamNum;
        int addedPlayers = 0;
        while (addedPlayers < playersNum) {
            IModelFactory playerFactory = hockeyContext.getModelFactory();
            IPlayer player = playerFactory.newPlayer();
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

    List<String> firstNameList = Arrays.asList("Adam", "Alex", "Aaron", "Ben", "Carl", "Dan", "David", "Edward", "Fred", "Frank", "George", "Hal", "Hank", "Ike", "John", "Jack", "Joe", "Larry", "Monte", "Matthew", "Mark", "Nathan", "Otto", "Paul", "Peter", "Roger", "Roger", "Steve", "Thomas", "Tim", "Ty", "Victor", "Walter", "Walter", "John", "Prashant", "Hardik", "Rashita", "Mary", "Fred", "Mohammed", "Raghav", "Bonnie", "Tami", "Chris", "Pat", "Sammy", "Abraham", "Tina", "Nancy", "Roger", "Mike", "Rob", "Zongming", "Wen", "Don", "Rahul", "Sai", "Prabhjot", "Mozhgan", "Shakuntala", "Karan", "Jimmy");

    List<String> lastNameList = Arrays.asList("Anderson", "Ashwoon", "Aikin", "Bateman", "Bongard", "Bowers", "Boyd", "Cannon", "Cast", "Deitz", "Dewalt", "Ebner", "Frick", "Hancock", "Haworth", "Hesch", "Hoffman", "Kassing", "Knutson", "Lawless", "Lawicki", "Mccord", "McCormack", "Miller", "Myers", "Nugent", "Ortiz", "Orwig", "Ory", "Paiser", "Pak", "Pettigrew", "Quinn", "Quizoz", "Ramachandran", "Resnick", "Sagar", "Schickowski", "Schiebel", "Sellon", "Severson", "Shaffer", "Solberg", "Soloman", "Sonderling", "Soukup", "Soulis", "Stahl", "Sweeney", "Tandy", "Trebil", "Trusela", "Trussel", "Turco", "Uddin", "Uflan", "Ulrich", "Upson", "Vader", "Vail", "Valente", "Van Zandt", "Vanderpoel", "Ventotla", "Vogal", "Wagle", "Wagner", "Wakefield", "Weinstein", "Weiss", "Woo", "Yang", "Yates", "Yocum", "Zeaser", "Zeller", "Ziegler", "Bauer", "Baxster", "Casal", "Cataldi", "Caswell", "Celedon", "Chambers", "Chapman", "Christensen", "Darnell", "Davidson", "Davis", "DeLorenzo", "Dinkins", "Doran", "Dugelman", "Dugan", "Duffman", "Eastman", "Ferro", "Ferry", "Fletcher", "Fietzer", "Hylan", "Hydinger", "Illingsworth", "Ingram", "Irwin", "Jagtap", "Jenson", "Johnson", "Johnsen", "Jones", "Jurgenson", "Kalleg", "Kaskel", "Keller", "Leisinger", "LePage", "Lewis", "Linde", "Lulloff", "Maki", "Martin", "McGinnis", "Mills", "Moody", "Moore", "Napier", "Nelson", "Norquist", "Nuttle", "Olson", "Ostrander", "Reamer", "Reardon", "Reyes", "Rice", "Ripka", "Roberts", "Rogers", "Root", "Sandstrom", "Sawyer", "Schlicht", "Schmitt", "Schwager", "Schutz", "Schuster", "Tapia", "Thompson", "Tiernan", "Tisler");

}
