package simulation.state;

import config.AppConfig;
import db.data.ILeagueFactory;
import presentation.ReadUserInput;
import simulation.factory.LeagueConcrete;
import simulation.model.*;
import presentation.ConsoleOutput;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import java.util.ArrayList;
import java.util.List;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class CreateTeamState implements IHockeyState {

    private HockeyContext hockeyContext;
    private League league;
    private String conferenceName;
    private String divisionName;
    private Team team;
    private List<Manager> managerList;
    private List<Coach> coachList;
    private FreeAgent freeAgent;
    private IUserInputForTeamCreation teamCreationInput;
    private IConsoleOutputForTeamCreation teamCreationOutput;
    private static final String UNABLETOLOADLEAGUE = "Unable to load league, please try again.";
    private static final String WAITMESSAGE = "Please wait while we are saving your league information...";
    private static final String CREATEANOTHERTEAMQUESTION = "Do you want to create another team? Yes/Y or No/N";
    private static final String Y="y";
    private static final String YES="yes";
    private static final String N="n";
    private static final String NO="no";
    private static final String CREATEORLOADTEAM = "createOrLoadTeam";
    private static final String HOWMANYSEASONS = "How many seasons do you want to simulate";
    private static final String RIGHTCHOICEREQUEST = "Please enter the right choice. Yes/Y or No/N";

    public CreateTeamState(HockeyContext hockeyContext, IUserInputForTeamCreation teamCreationInput,
                           IConsoleOutputForTeamCreation teamCreationOutput) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.teamCreationInput = teamCreationInput;
        this.teamCreationOutput = teamCreationOutput;
    }

    public CreateTeamState() {

    }


    @Override
    public void entry() {
        if (isLeaguePresent(league.getName())) {
            teamCreationOutput.showLeagueAlreadyExistsError();
            System.out.println();
            System.exit(1);
        }
        if(hasEnoughCoaches(league.getCoachList()) && hasEnoughFreeAgent(league.getFreeAgent()) && hasEnoughManagers(league.getManagerList())){
            Conference conference = chooseConference();
            Division division = chooseDivision(conference);
            getTeamName(division);


            chooseManager();
            chooseCoach();
            choosePlayers();
        }else{
            teamCreationOutput.showNotEnoughMembersError();
            exit();
        }
    }

    public boolean hasEnoughManagers(List<Manager> managerList){
        if(managerList == null){
            return false;
        }
        if(managerList.size()>=1){
            return true;
        }else{
            return false;
        }
    }

    public boolean hasEnoughCoaches(List<Coach> coachList){
        if(coachList == null){
            return false;
        }
        if(coachList.size()>=1) {
            return true;
        }else{
            return false;
        }
    }

    public boolean hasEnoughFreeAgent(FreeAgent freeAgent){
        if(freeAgent == null){
            return false;
        }
        List<Player> freeAgentList = freeAgent.getPlayerList();
        int countOfGoalie = 0;
        int countOfSkaters = 0;
        for(int i=0;i<freeAgentList.size();i++){
            if (freeAgentList.get(i).getPosition().toString().equals("goalie")){
                countOfGoalie++;
            }else{
                countOfSkaters++;
            }
        }
        if(countOfGoalie>=2 && countOfSkaters>=18){
            return true;
        }else{
            return false;
        }
    }

    private void getTeamName(Division division) {
        if(division == null){
            return;
        }
        List<String> teamNameList = division.getTeamNameList();
        String teamName = teamCreationInput.getTeamName(teamNameList);
        team = new Team();
        team.setName(teamName);
        team.setAiTeam(false);
    }

    private Division chooseDivision(Conference conference) {
        if(conference == null){
            return null;
        }
        List<String> divisionNameList = conference.getDivisionNameList();
        divisionName = teamCreationInput.getDivisionName(divisionNameList);
        return conference.getDivisionFromListByName(divisionName);
    }

    private Conference chooseConference() {
        List<String> conferenceNameList = league.createConferenceNameList();
        conferenceName = teamCreationInput.getConferenceName(conferenceNameList);
        return league.getConferenceFromListByName(conferenceName);
    }

    private void choosePlayers() {
        freeAgent = league.getFreeAgent();
        List<Player> freeAgentList = freeAgent.getPlayerList();
        List<Integer> chosenPlayersIdList = team.createChosenPlayerIdList(freeAgent);
        teamCreationOutput.showTeamCreationWaitMessage();
        List<Player> teamPlayers = createPlayerListByChosenPlayerId(chosenPlayersIdList, freeAgentList);
        freeAgentList = removeChosenPlayersFromFreeAgentList(chosenPlayersIdList, freeAgentList);
        freeAgent.setPlayerList(freeAgentList);
        team.setPlayerList(teamPlayers);
    }

    private void chooseCoach() {
        coachList = league.getCoachList();
        teamCreationOutput.showCoachListOnScreen(coachList);
        int headCoachId = teamCreationInput.getHeadCoachId(coachList);
        Coach headCoach = new Coach(coachList.get(headCoachId));
        team.setCoach(headCoach);
        coachList = league.removeCoachFromCoachListById(coachList, headCoachId);
        teamCreationOutput.showSuccessfulCoachCreationMessage();
    }

    private void chooseManager() {
        managerList = league.getManagerList();
        teamCreationOutput.showManagerListOnScreen(managerList);
        int generalManagerId = teamCreationInput.getGeneralManagerId(managerList);
        Manager generalManager = new Manager(managerList.get(generalManagerId));
        team.setManager(generalManager);
        managerList = league.removeManagerFromManagerListById(managerList, generalManagerId);
        teamCreationOutput.showSuccessfulManagerCreationMessage();
    }

    public List<Player> createPlayerListByChosenPlayerId(List<Integer> chosenPlayersIdList, List<Player> freeAgentList) {
        if(chosenPlayersIdList == null || freeAgentList == null){
            return null;
        }
        List<Player> teamPlayers = new ArrayList<>();
        for (int freeAgentIndex : chosenPlayersIdList) {
            teamPlayers.add(new Player(freeAgentList.get(freeAgentIndex)));
        }
        return teamPlayers;
    }

    public List<Player> removeChosenPlayersFromFreeAgentList(List<Integer> chosenPlayersIdList, List<Player> freeAgentList) {
        if(chosenPlayersIdList == null || freeAgentList == null){
            return null;
        }
        List<Player> newFreeAgentList = new ArrayList<>();
        for (int i = 0; i < freeAgentList.size(); i++) {
            boolean notChosen = (!chosenPlayersIdList.contains(i));
            if (notChosen) {
                Player newFreeAgent = new Player(freeAgentList.get(i));
                newFreeAgent.setIsFreeAgent(true);
                newFreeAgentList.add(newFreeAgent);
            }
        }
        return newFreeAgentList;
    }

    private boolean isLeaguePresent(String leagueName) {
        LeagueConcrete leagueConcrete = AppConfig.getInstance().getLeagueConcrete();
        ILeagueFactory loadLeagueFactory = leagueConcrete.newLoadLeagueFactory();
        League league = null;
        try {
            int userId = hockeyContext.getUser().getId();
            league = leagueConcrete.createLeagueFromNameAndUserId(leagueName, userId, loadLeagueFactory);
        } catch (Exception e) {
            ConsoleOutput.printToConsole(UNABLETOLOADLEAGUE);
            System.exit(1);
            e.printStackTrace();
        }
        return null != league && league.getId() > 0;
    }


    @Override
    public void process() {
        league.setCreatedBy(hockeyContext.getUser().getId());
        league.setManagerList(managerList);
        league.setCoachList(coachList);
        league.setFreeAgent(freeAgent);
        List<Conference> conferenceList = league.getConferenceList();
        for (Conference conference : conferenceList) {
            if (conference.getName().toLowerCase().equals(conferenceName.toLowerCase())) {
                List<Division> divisionList = conference.getDivisionList();
                for (Division division : divisionList) {
                    if (division.getName().toLowerCase().equals(divisionName.toLowerCase())) {
                        division.getTeamList().add(team);
                    }
                }
                conference.setDivisionList(divisionList);
            }
        }
        league.setConferenceList(conferenceList);
        ConsoleOutput.printToConsole(WAITMESSAGE);
    }

    @Override
    public IHockeyState exit() {
        IHockeyState hockeyState = null;
        String createAnotherTeam = ReadUserInput.getUserInput(CREATEANOTHERTEAMQUESTION);
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
                ConsoleOutput.printToConsole(RIGHTCHOICEREQUEST);
            }
        }
        return hockeyState;
    }
}