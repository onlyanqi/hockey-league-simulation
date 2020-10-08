package state;

import dao.*;
import data.*;
import model.*;
import org.icehockey.GetInput;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static common.Constants.loadLeagueByName;

public class CreateTeamState implements IHockeyState {

    private HockeyContext hockeyContext;
    private League league;
    private String conferenceName;
    private String divisionName;
    private String teamName;
    private String generalManagerName;
    private String headCoachName;
    private String seasonName;


    public CreateTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getLeague();
    }

    @Override
    public void entry() throws Exception {


        if(isLeaguePresent(league.getName())){
            System.out.println("League already exists. Please enter a new one");
            System.exit(1);
        }

        seasonName  = GetInput.getUserInput("Please enter season name");

        while((seasonName.isEmpty() || seasonName ==null )){
            seasonName = GetInput.getUserInput("Please enter season name!");
        }


        conferenceName  = GetInput.getUserInput("Please enter conference name the team belongs to");
        List<Conference> conferenceList =  league.getConferenceList();


        for(Conference conference: conferenceList ){
            while(!(conference.getName().equals(conferenceName))){
                conferenceName  = GetInput.getUserInput("Please enter conference name from the existing ones");
            }
        }
        divisionName  = GetInput.getUserInput("Please enter division name the team belongs to");


        for(Conference conference : conferenceList){
            for(Division division : conference.getDivisionList()){
                while(!(division.getName().equals(divisionName))){
                    divisionName  = GetInput.getUserInput("Please enter division name from the existing ones");
                }
            }
        }
        teamName  = GetInput.getUserInput("Please enter team name");

        while((teamName.isEmpty() || teamName ==null || isTeamPresent(teamName))){
            teamName = GetInput.getUserInput("Please enter valid team name! Make sure there is no existing team with provided name");
        }
        generalManagerName  = GetInput.getUserInput("Please enter name of general manager");
        while(generalManagerName.isEmpty() || generalManagerName ==null){
            generalManagerName=  GetInput.getUserInput("Please enter GeneralManager name!");
        }
        headCoachName  = GetInput.getUserInput("Please enter name of head coach ");
        while(headCoachName.isEmpty() || headCoachName ==null){
            headCoachName = GetInput.getUserInput("Please enter  HeadCoach Name !");
        }

    }

    private boolean isTeamPresent(String teamName)  {
        ILoadTeamFactory factory = new LoadTeamDao();
        Team team = null;
        try {
            team = factory.loadTeamByName(teamName);
        }catch (Exception e) {
            System.out.println("Unable to load team, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if(team!=null) return true;
        else return false;

    }

    private boolean isLeaguePresent(String leagueName){

        ILoadLeagueFactory iLoadLeagueFactory = new LoadLeagueDao();

        League league = null;
        try {
            league = iLoadLeagueFactory.loadLeagueByName(league.getName());
        }catch (Exception e) {
            System.out.println("Unable to load league, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if(league!=null) return true;
        else return false;
    }


    @Override
    public void process() {
        //Instantiate Model Objects

        List<Conference> conferenceList = league.getConferenceList();
        for(Conference conference : conferenceList ){
            if(conference.getName().equals(conferenceName)){
                List<Division> divisionList  = conference.getDivisionList();
                for(Division division: divisionList){
                    if(division.getName().equals(divisionName)) {
                        Team team = new Team();
                        team.setName(teamName);
                        team.setHeadCoach(headCoachName);
                        team.setGeneralManager(generalManagerName);

                        division.getTeamList().add(team);
                    }
                }
                conference.setDivisionList(divisionList);
            }
        }
        league.setConferenceList(conferenceList);
        hockeyContext.setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        PlayerChoiceState playerChoiceState = null;
        if (league != null) {
            //Persist to DB and transition to next state
            league.setCreatedBy(3);
            IAddLeagueFactory leagueDao = new AddLeagueDao();
            IAddConferenceFactory conferenceDao = new AddConferenceDao();
            IAddDivisionFactory divisionDao = new AddDivisionDao();
            IAddTeamFactory teamDao = new AddTeamDao();


            IAddSeasonFactory seasonDao = new AddSeasonDao();

            Season season = new Season();
            season.setName(seasonName);

            try {
                int leagueId = leagueDao.addLeague(league);
                int seasonId = seasonDao.addSeason(season);

                if(leagueId != 0 && seasonId != 0){
                    if(league.getFreeAgent() != null) {
                        int freeAgentId = addFreeAgent(leagueId, seasonId);
                        addPlayerList(0,freeAgentId, seasonId, league.getFreeAgent().getPlayerList());
                    }

                    if(league.getConferenceList() != null && !league.getConferenceList().isEmpty()){
                        for (Conference conference : league.getConferenceList()) {
                            conference.setLeagueId(leagueId);
                            int conferenceId = conferenceDao.addConference(conference);

                            for (Division division : conference.getDivisionList()) {
                                division.setConferenceId(conferenceId);
                                int divisionId = divisionDao.addDivision(division);

                                for (Team team : division.getTeamList()) {
                                    team.setDivisionId(divisionId);
                                    int teamId = teamDao.addTeam(team);
                                    addPlayerList(teamId,0, seasonId, team.getPlayerList());
                                }
                            }

                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Unable to save the league! Please try again");
                System.exit(1);
                e.printStackTrace();
            }
            playerChoiceState = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "createOrLoadTeam");

        }
        return playerChoiceState;
    }

    private int addFreeAgent(int leagueId, int seasonId) throws Exception {
        IAddFreeAgentFactory freeAgentDao = new AddFreeAgentDao();
        FreeAgent freeAgent = league.getFreeAgent();
        freeAgent.setSeasonId(seasonId);
        freeAgent.setLeagueId(leagueId);
        return freeAgentDao.addFreeAgent(freeAgent);
    }

    private void addPlayerList(int teamId, int freeAgentId, int seasonId, List<Player> playerList) throws Exception {
        if(playerList != null && !playerList.isEmpty()) {
            IAddPlayerFactory playerDao = new AddPlayerDao();
            for (Player player : playerList) {
                player.setFreeAgentId(teamId);
                player.setFreeAgentId(freeAgentId);
                player.setSeasonId(seasonId);
                playerDao.addPlayer(player);
            }
        }
    }
}
