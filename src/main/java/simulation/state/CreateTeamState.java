package simulation.state;

import config.AppConfig;
import db.data.ILeagueFactory;
import simulation.factory.LeagueConcrete;
import simulation.model.*;
import simulation.serializers.LeagueDataSerializer;
import userIO.ConsoleOutput;
import userIO.GetInput;
import userIO.IConsoleOutputForTeamCreation;
import userIO.IUserInputForTeamCreation;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamState implements IHockeyState, ICreateTeamState {

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

    public CreateTeamState(HockeyContext hockeyContext, IUserInputForTeamCreation teamCreationInput,
                           IConsoleOutputForTeamCreation teamCreationOutput) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
        this.teamCreationInput = teamCreationInput;
        this.teamCreationOutput = teamCreationOutput;
    }

    @Override
    public void entry() {
        if (isLeaguePresent(league.getName())) {
            teamCreationOutput.showLeagueAlreadyExistsError();
            System.out.println();
            System.exit(1);
        }

        Conference conference = chooseConference();
        Division division = chooseDivision(conference);
        getTeamName(division);

        chooseManager();
        chooseCoach();
        choosePlayers();
    }

    @Override
    public void getTeamName(Division division) {
        List<String> teamNameList = division.getTeamNameList();
        String teamName = teamCreationInput.getTeamName(teamNameList);
        team = new Team();
        team.setName(teamName);
        team.setAiTeam(false);
    }

    @Override
    public Division chooseDivision(Conference conference) {
        List<String> divisionNameList = conference.getDivisionNameList();
        divisionName = teamCreationInput.getDivisionName(divisionNameList);
        return conference.getDivisionFromListByName(divisionName);
    }

    @Override
    public Conference chooseConference() {
        List<String> conferenceNameList = league.createConferenceNameList();
        conferenceName = teamCreationInput.getConferenceName(conferenceNameList);
        return league.getConferenceFromListByName(conferenceName);
    }

    @Override
    public void choosePlayers() {
        freeAgent = league.getFreeAgent();
        List<Player> freeAgentList = freeAgent.getPlayerList();
        List<Integer> chosenPlayersIdList = team.createChosenPlayerIdList(freeAgent);
        teamCreationOutput.showTeamCreationWaitMessage();
        List<Player> teamPlayers = createPlayerListByChosenPlayerId(chosenPlayersIdList, freeAgentList);
        freeAgentList = removeChosenPlayersFromFreeAgentList(chosenPlayersIdList, freeAgentList);
        freeAgent.setPlayerList(freeAgentList);
        team.setPlayerList(teamPlayers);
    }

    @Override
    public void chooseCoach() {
        coachList = league.getCoachList();
        teamCreationOutput.showCoachListOnScreen(coachList);
        int headCoachId = teamCreationInput.getHeadCoachId(coachList);
        Coach headCoach = new Coach(coachList.get(headCoachId));
        team.setCoach(headCoach);
        coachList = league.removeCoachFromCoachListById(coachList, headCoachId);
        teamCreationOutput.showSuccessfulCoachCreationMessage();
    }

    @Override
    public void chooseManager() {
        managerList = league.getManagerList();
        teamCreationOutput.showManagerListOnScreen(managerList);
        int generalManagerId = teamCreationInput.getGeneralManagerId(managerList);
        Manager generalManager = new Manager(managerList.get(generalManagerId));
        team.setManager(generalManager);
        managerList = league.removeManagerFromManagerListById(managerList, generalManagerId);
        teamCreationOutput.showSuccessfulManagerCreationMessage();
    }

    @Override
    public List<Player> createPlayerListByChosenPlayerId(List<Integer> chosenPlayersIdList, List<Player> freeAgentList) {
        List<Player> teamPlayers = new ArrayList<>();
        for (int freeAgentIndex : chosenPlayersIdList) {
            teamPlayers.add(new Player(freeAgentList.get(freeAgentIndex)));
        }
        return teamPlayers;
    }

    @Override
    public List<Player> removeChosenPlayersFromFreeAgentList(List<Integer> chosenPlayersIdList, List<Player> freeAgentList) {
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

    @Override
    public boolean isLeaguePresent(String leagueName) {
        LeagueConcrete leagueConcrete = AppConfig.getInstance().getLeagueConcrete();
        ILeagueFactory loadLeagueFactory = leagueConcrete.newLoadLeagueFactory();
        League league = null;
        try {
            int userId = hockeyContext.getUser().getId();
            league = leagueConcrete.createLeagueFromNameAndUserId(leagueName, userId, loadLeagueFactory);
        } catch (Exception e) {
            ConsoleOutput.printToConsole("Unable to load league, please try again.");
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
    }

    @Override
    public IHockeyState exit() {

        ConsoleOutput.printToConsole("Please wait while we are saving your league information...");
        IHockeyState hockeyState = null;

        String createAnotherTeam = GetInput.getUserInput("Do you want to create another team? Yes/Y or No/N");
        while (createAnotherTeam != null) {

            if (createAnotherTeam.toLowerCase().equals("y") || createAnotherTeam.toLowerCase().equals("yes")) {
                IUserInputForTeamCreation inputForTeamCreation = AppConfig.getInstance().getInputForTeamCreation();
                IConsoleOutputForTeamCreation outputForTeamCreation = AppConfig.getInstance().getOutputForTeamCreation();
                hockeyState = new CreateTeamState(hockeyContext,
                        inputForTeamCreation, outputForTeamCreation);
                break;
            } else if (createAnotherTeam.toLowerCase().equals("n") || createAnotherTeam.toLowerCase().equals("no")) {
                hockeyContext.getUser().setLeague(league);
                hockeyState = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "createOrLoadTeam");
                break;
            } else {
                ConsoleOutput.printToConsole("Please enter the right choice. Yes/Y or No/N");
            }
        }
        return hockeyState;
    }
}