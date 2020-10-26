package simulation.state;

import db.data.*;
import scala.tools.jline_embedded.console.UserInterruptException;
import simulation.factory.*;
import simulation.serializers.LeagueDataSerializer;
import userIO.*;
import simulation.model.*;
import java.util.ArrayList;
import java.util.List;

public class CreateTeamState implements IHockeyState {

    private HockeyContext hockeyContext;
    private League league;
    private String conferenceName;
    private String divisionName;
    private String teamName;
    private Manager generalManager;
    private Coach headCoach;
    private Team team;
    private List<Player> teamPlayersList;
    private List<Manager> managerList;
    private List<Coach> coachList;
    private List<Player> teamPlayers;
    private List<Player> freeAgentList;
    private FreeAgent freeAgent;

    public CreateTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public void entry() {
        IUserInputForTeamCreation teamCreationInput = new UseInputForTeamCreation();
        IConsoleOutputForTeamCreation teamCreationOutput = new ConsoleOutputForTeamCreation();
        if(isLeaguePresent(league.getName())){
            teamCreationOutput.showLeagueAlreadyExistsError();
            System.out.println();
            System.exit(1);
        }

        List<String> conferenceNameList = league.createConferenceNameList();
        conferenceName  = teamCreationInput.getConferenceName(conferenceNameList);
        Conference conference =league.getConferenceFromListByName(conferenceName);

        List<String> divisionNameList = conference.getDivisionNameList();
        divisionName = teamCreationInput.getDivisionName(divisionNameList);
        Division division = conference.getDivisionFromListByName(divisionName);

        List<String> teamNameList = division.getTeamNameList();
        teamName  = teamCreationInput.getTeamName(teamNameList);
        team = new Team();
        team.setName(teamName);

        managerList = league.getManagerList();
        teamCreationOutput.showManagerListOnScreen(managerList);
        int generalManagerId = teamCreationInput.getGeneralManagerId(managerList);
        generalManager = new Manager(managerList.get(generalManagerId));
        team.setManager(generalManager);
        managerList=league.removeManagerFromManagerListById(managerList, generalManagerId);
        teamCreationOutput.showSuccessfulManagerCreationMessage();

        coachList = league.getCoachList();
        teamCreationOutput.showCoachListOnScreen(coachList);
        int headCoachId  = teamCreationInput.getHeadCoachId(coachList);
        headCoach = new Coach(coachList.get(headCoachId));
        team.setCoach(headCoach);
        coachList=league.removeCoachFromCoachListById(coachList,headCoachId);
        teamCreationOutput.showSuccessfulCoachCreationMessage();

        freeAgent = league.getFreeAgent();
        freeAgentList=freeAgent.getPlayerList();
        List<Integer> chosenPlayersIdList = team.createChosenPlayerIdList(freeAgent);
        teamCreationOutput.showTeamCreationWaitMessage();

        teamPlayers= createPlayerListByChosenPlayerId(chosenPlayersIdList, freeAgentList);
        freeAgentList=removeChosenPlayersFromFreeAgentList(chosenPlayersIdList, freeAgentList);
        freeAgent.setPlayerList(freeAgentList);
        team.setPlayerList(teamPlayers);
        teamPlayersList=teamPlayers;
    }


    private List<Player> createPlayerListByChosenPlayerId(List<Integer> chosenPlayersIdList, List<Player> freeAgentList){
        List<Player> teamPlayers = new ArrayList<>();
        for(int i=0;i<chosenPlayersIdList.size();i++){
            int freeAgentIndex = chosenPlayersIdList.get(i);
            teamPlayers.add(new Player(freeAgentList.get(freeAgentIndex)));
        }
        return teamPlayers;
    }

    private List<Player> removeChosenPlayersFromFreeAgentList(List<Integer> chosenPlayersIdList, List<Player> freeAgentList){
        List<Player> newFreeAgentList= new ArrayList<>();
        for(int i=0;i<freeAgentList.size();i++){
            if(!chosenPlayersIdList.contains(i)){
                Player newFreeAgent = new Player(freeAgentList.get(i));
                newFreeAgent.setIsFreeAgent(true);
                newFreeAgentList.add(newFreeAgent);
            }
        }
        return newFreeAgentList;
    }

    private boolean isLeaguePresent(String leagueName){
        LeagueConcrete leagueConcrete = new LeagueConcrete();
        ILeagueFactory loadLeagueFactory = leagueConcrete.newLoadLeagueFactory();
        League league = null;
        try {
            int userId = hockeyContext.getUser().getId();
            league = leagueConcrete.newLeagueNameUserId(leagueName, userId, loadLeagueFactory);
        }catch (Exception e) {
            System.out.println("Unable to load league, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if(league!=null && league.getId() > 0) return true;
        else return false;
    }


    @Override
    public void process() {

        league.setCreatedBy(hockeyContext.getUser().getId());
        league.setManagerList(managerList);
        league.setCoachList(coachList);
        league.setFreeAgent(freeAgent);
        List<Conference> conferenceList = league.getConferenceList();
        for(Conference conference : conferenceList ){
            if(conference.getName().toLowerCase().equals(conferenceName.toLowerCase())){
                List<Division> divisionList  = conference.getDivisionList();
                for(Division division: divisionList){
                    if(division.getName().toLowerCase().equals(divisionName.toLowerCase())) {
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
        IUserInputForTeamCreation userInputForTeamCreation = new UseInputForTeamCreation();
        IConsoleOutputForTeamCreation teamCreationOutput = new ConsoleOutputForTeamCreation();

        System.out.println("Please wait while we are saving your league information...");

        IHockeyState hockeyState = null;

            String createAnotherTeam = GetInput.getUserInput("Do you want to create another team? Yes/Y or No/N");
            while(createAnotherTeam !=null){

                if(createAnotherTeam.toLowerCase().equals("y") || createAnotherTeam.toLowerCase().equals("yes")  ){
                    hockeyState = new CreateTeamState(hockeyContext);
                    break;
                }else if(createAnotherTeam.toLowerCase().equals("n") || createAnotherTeam.toLowerCase().equals("no")){
                    String performSerialization = userInputForTeamCreation.getUserChoiceForSerialization();
                    if(performSerialization.toLowerCase().equals("y") || performSerialization.toLowerCase().equals("Yes")){
                        LeagueDataSerializer dataSerializer = new LeagueDataSerializer();
                        dataSerializer.serialize(league);
                        hockeyContext.getUser().setLeague(league);
                        teamCreationOutput.showSuccessfulSerializationMessage();
                    }
                    hockeyState = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "createOrLoadTeam");
                    break;
                }else{
                    System.out.println("Please enter the right choice. Yes/Y or No/N");
                }
            }
        return hockeyState;
    }

}