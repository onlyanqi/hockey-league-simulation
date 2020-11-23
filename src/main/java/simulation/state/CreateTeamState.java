package simulation.state;

import config.AppConfig;
import presentation.ConsoleOutput;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import presentation.ReadUserInput;
import simulation.factory.ICoachFactory;
import simulation.factory.ITeamFactory;
import simulation.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class CreateTeamState implements IHockeyState {

    private static final String UNABLETOLOADLEAGUE = "Unable to load league, please try again.";
    private static final String WAITMESSAGE = "Please wait while we are saving your league information...";
    private static final String CREATEANOTHERTEAMQUESTION = "Do you want to create another team? Yes/Y or No/N";
    private static final String Y = "y";
    private static final String YES = "yes";
    private static final String N = "n";
    private static final String NO = "no";
    private static final String CREATEORLOADTEAM = "createOrLoadTeam";
    private static final String HOWMANYSEASONS = "How many seasons do you want to simulate";
    private static final String RIGHTCHOICEREQUEST = "Please enter the right choice. Yes/Y or No/N";
    private static final String GOALIE = "goalie";
    private IHockeyContext hockeyContext;
    private ILeague league;
    private String conferenceName;
    private String divisionName;
    private ITeam team;
    private List<IManager> managerList;
    private List<ICoach> coachList;
    private IFreeAgent freeAgent;
    private IUserInputForTeamCreation teamCreationInput;
    private IConsoleOutputForTeamCreation teamCreationOutput;
    private ConsoleOutput consoleOutput = null;
    private ReadUserInput readUserInput = null;

    public CreateTeamState(IHockeyContext hockeyContext, IUserInputForTeamCreation teamCreationInput,
                           IConsoleOutputForTeamCreation teamCreationOutput) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.teamCreationInput = teamCreationInput;
        this.teamCreationOutput = teamCreationOutput;
        consoleOutput = ConsoleOutput.getInstance();
        readUserInput = ReadUserInput.getInstance();
    }

    public CreateTeamState() {

    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    @Override
    public void entry() {
        if (isLeaguePresent(league.getName())) {
            teamCreationOutput.showLeagueAlreadyExistsError();
            System.out.println();
            System.exit(1);
        }
        if (hasEnoughCoaches(league.getCoachList()) && hasEnoughFreeAgent(league.getFreeAgent()) && hasEnoughManagers(league.getManagerList())) {
            IConference conference = chooseConference();
            IDivision division = chooseDivision(conference);
            getTeamName(division);

            chooseManager();
            chooseCoach();
            choosePlayers();
        } else {
            teamCreationOutput.showNotEnoughMembersError();
            exit();
        }
    }

    public boolean hasEnoughManagers(List<IManager> managerList) {
        if (managerList == null) {
            return false;
        }
        if (managerList.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEnoughCoaches(List<ICoach> coachList) {
        if (coachList == null) {
            return false;
        }
        if (coachList.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasEnoughFreeAgent(IFreeAgent freeAgent) {
        if (freeAgent == null) {
            return false;
        }
        List<IPlayer> freeAgentList = freeAgent.getPlayerList();
        int countOfGoalie = 0;
        int countOfForward = 0;
        int countOfDefense = 0;
        for (IPlayer player : freeAgentList) {
            if (player.getPosition() == Position.GOALIE) {
                countOfGoalie++;
            } else if (player.getPosition() == Position.FORWARD) {
                countOfForward++;
            } else {
                countOfDefense++;
            }
        }
        if (countOfGoalie >= 4 && countOfForward >= 16 && countOfDefense >= 10) {
            return true;
        } else {
            return false;
        }
    }

    private void getTeamName(IDivision division) {
        if (division == null) {
            return;
        }
        List<String> teamNameList = division.getTeamNameList();
        String teamName = teamCreationInput.getTeamName(teamNameList);
        ITeamFactory teamFactory = hockeyContext.getTeamFactory();
        team = teamFactory.newTeam();
        team.setName(teamName);
        team.setAiTeam(false);
    }

    private IDivision chooseDivision(IConference conference) {
        if (conference == null) {
            return null;
        }
        List<String> divisionNameList = conference.getDivisionNameList();
        divisionName = teamCreationInput.getDivisionName(divisionNameList);
        return conference.getDivisionFromListByName(divisionName);
    }

    private IConference chooseConference() {
        List<String> conferenceNameList = league.createConferenceNameList();
        conferenceName = teamCreationInput.getConferenceName(conferenceNameList);
        return league.getConferenceFromListByName(conferenceName);
    }

    private void choosePlayers() throws IllegalArgumentException {
        freeAgent = league.getFreeAgent();
        List<IPlayer> freeAgentList = freeAgent.getPlayerList();
        List<Integer> chosenPlayersIdList = team.createChosenPlayerIdList(freeAgent);
        teamCreationOutput.showTeamCreationWaitMessage();
        List<IPlayer> teamPlayers = createPlayerListByChosenPlayerId(chosenPlayersIdList, freeAgentList);
        freeAgentList = removeChosenPlayersFromFreeAgentList(chosenPlayersIdList, freeAgentList);
        freeAgent.setPlayerList(freeAgentList);
        for(Player player :teamPlayers ){
            player.setTeamId(team.getId());
        }
        team.setPlayerList(teamPlayers);
        team.setStrength();
    }

    private void chooseCoach() {
        coachList = league.getCoachList();
        teamCreationOutput.showCoachListOnScreen(coachList);
        int headCoachId = teamCreationInput.getHeadCoachId(coachList);
        ICoachFactory coachFactory = hockeyContext.getCoachFactory();
        ICoach headCoach = coachFactory.newCoachWithCoach(coachList.get(headCoachId));
        team.setCoach(headCoach);
        coachList = league.removeCoachFromCoachListById(coachList, headCoachId, coachFactory);
        teamCreationOutput.showSuccessfulCoachCreationMessage();
    }

    private void chooseManager() {
        managerList = league.getManagerList();
        teamCreationOutput.showManagerListOnScreen(managerList);
        //int generalManagerId = teamCreationInput.getGeneralManagerId(managerList);
        int generalManagerId = 0;

        Manager generalManager = new Manager(managerList.get(generalManagerId));
        team.setManager(generalManager);
        managerList = league.removeManagerFromManagerListById(managerList, generalManagerId);
        teamCreationOutput.showSuccessfulManagerCreationMessage();
    }

    public List<IPlayer> createPlayerListByChosenPlayerId(List<Integer> chosenPlayersIdList, List<IPlayer> freeAgentList) {
        if (chosenPlayersIdList == null || freeAgentList == null) {
            return null;
        }
        List<IPlayer> teamPlayers = new ArrayList<>();
        for (int freeAgentIndex : chosenPlayersIdList) {
            Player chosenPlayer = new Player(freeAgentList.get(freeAgentIndex));
            chosenPlayer.setIsFreeAgent(false);
            teamPlayers.add(chosenPlayer);
        }
        return teamPlayers;
    }

    public List<IPlayer> removeChosenPlayersFromFreeAgentList(List<Integer> chosenPlayersIdList, List<IPlayer> freeAgentList) {
        if (chosenPlayersIdList == null || freeAgentList == null) {
            return null;
        }
        List<IPlayer> newFreeAgentList = new ArrayList<>();
        for (int i = 0; i < freeAgentList.size(); i++) {
            boolean notChosen = (!chosenPlayersIdList.contains(i));
            if (notChosen) {
                IPlayer newFreeAgent = new Player(freeAgentList.get(i));
                newFreeAgent.setIsFreeAgent(true);
                newFreeAgentList.add(newFreeAgent);
            }
        }
        return newFreeAgentList;
    }

    private boolean isLeaguePresent(String leagueName) {
        /*LeagueConcrete leagueConcrete = AppConfig.getInstance().getLeagueConcrete();
        ILeagueFactory loadLeagueFactory = leagueConcrete.newLoadLeagueFactory();
        League league = null;
        try {
            int userId = hockeyContext.getUser().getId();
            league = leagueConcrete.createLeagueFromNameAndUserId(leagueName, userId, loadLeagueFactory);
        } catch (Exception e) {
            consoleOutput.printMsgToConsole(UNABLETOLOADLEAGUE);
            System.exit(1);
        }

        boolean isPresent = false;

        if(league == null){
            isPresent = false;
        } else if (league.getId() > 0){
            isPresent = true;
        }*/
        return false;
    }

    @Override
    public void process() {
        league.setCreatedBy(hockeyContext.getUser().getId());
        league.setUser(hockeyContext.getUser().getName());
        league.setManagerList(managerList);
        league.setCoachList(coachList);
        league.setFreeAgent(freeAgent);
        List<IConference> conferenceList = league.getConferenceList();
        for (IConference conference : conferenceList) {
            if (conference.getName().toLowerCase().equals(conferenceName.toLowerCase())) {
                List<IDivision> divisionList = conference.getDivisionList();
                for (IDivision division : divisionList) {
                    if (division.getName().toLowerCase().equals(divisionName.toLowerCase())) {
                        division.getTeamList().add(team);
                    }
                }
                conference.setDivisionList(divisionList);
            }
        }
        league.setConferenceList(conferenceList);
        if(team == null){
            return;
        }
        league.setUserCreatedTeamName(team.getName());
        consoleOutput.printMsgToConsole(WAITMESSAGE);
    }

    @Override
    public IHockeyState exit() {

        consoleOutput.printMsgToConsole(WAITMESSAGE);
        IHockeyState hockeyState = null;

        String createAnotherTeam = readUserInput.getInput(CREATEANOTHERTEAMQUESTION);
        while (isNotEmpty(createAnotherTeam)) {
            if (createAnotherTeam.toLowerCase().equals(Y) || createAnotherTeam.toLowerCase().equals(YES)) {
                IUserInputForTeamCreation inputForTeamCreation = AppConfig.getInstance().getInputForTeamCreation();
                IConsoleOutputForTeamCreation outputForTeamCreation = AppConfig.getInstance().getOutputForTeamCreation();
                hockeyState = new CreateTeamState(hockeyContext,
                        inputForTeamCreation, outputForTeamCreation);
                break;
            } else if (createAnotherTeam.toLowerCase().equals(N) || createAnotherTeam.toLowerCase().equals(NO)) {
                hockeyContext.getUser().setLeague(league);
                hockeyState = new PlayerChoiceState(hockeyContext, HOWMANYSEASONS, CREATEORLOADTEAM);
                break;
            } else {
                createAnotherTeam = readUserInput.getInput(RIGHTCHOICEREQUEST);
            }
        }
        return hockeyState;
    }
}