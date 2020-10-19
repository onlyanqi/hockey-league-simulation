package simulation.state;

import db.data.*;
import simulation.factory.*;
import userIO.GetInput;
import simulation.model.*;
import java.util.ArrayList;
import java.util.List;

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
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public void entry() {

        if(isLeaguePresent(league.getName())){
            System.out.println("League already exists. Please enter a new one");
            System.exit(1);
        }

        seasonName  = GetInput.getUserInput("Please enter season name");


        while((seasonName ==null || seasonName.isEmpty() )){
            seasonName = GetInput.getUserInput("Please enter season name!");
        }

        conferenceName  = GetInput.getUserInput("Please enter conference name the team belongs to");

        List<Conference> conferenceList =  league.getConferenceList();



        List<String> conferenceNameList = new ArrayList<>();
        for(Conference conference : conferenceList){
            conferenceNameList.add(conference.getName().toLowerCase());
        }
        while(!conferenceNameList.contains(conferenceName.toLowerCase())){
            conferenceName  = GetInput.getUserInput("Please enter conference name from the existing ones");
        }

        Conference conference = null;

        for(Conference confer : conferenceList){
            if(confer.getName().toLowerCase().equals(conferenceName.toLowerCase())){
                conference = confer;
                break;
            }
        }

        List<String> divisionNameList = new ArrayList<>();
        for(Division division:conference.getDivisionList()){
            divisionNameList.add(division.getName().toLowerCase());
        }

        divisionName  = GetInput.getUserInput("Please enter division name the team belongs to");

        while(!divisionNameList.contains(divisionName.toLowerCase())){
            divisionName  = GetInput.getUserInput("Please enter division name from the existing ones");
        }

        Division division = null;
        for(Division division1 : conference.getDivisionList()){
            if(division1.getName().toLowerCase().equals(divisionName.toLowerCase())){
                division = division1;
                break;
            }
        }

        List<String> teamNameList = new ArrayList<>();
        for(Team team: division.getTeamList()){
            teamNameList.add(team.getName().toLowerCase());
        }

        teamName  = GetInput.getUserInput("Please enter a team name to create a team ");

        while(teamNameList.contains(teamName.toLowerCase())){
            teamName  = GetInput.getUserInput("Provided team name  already exists. Please enter a new one!");
        }


        while((teamName.isEmpty() || teamName ==null || isTeamPresent(teamName))){
            teamName = GetInput.getUserInput("Please enter valid team name! Make sure there is no existing team with provided name");
        }

        /*
        * Add logic to show current set of General managers
        *
        * */
        generalManagerName  = GetInput.getUserInput("Please enter name of general manager");

        while(generalManagerName.isEmpty() || generalManagerName ==null){
            generalManagerName=  GetInput.getUserInput("Please enter GeneralManager name!");
        }

        /*
         * Add logic to show current set of Head coach
         *
         * */
        headCoachName  = GetInput.getUserInput("Please enter name of head coach");

        while(headCoachName.isEmpty() || headCoachName ==null){
            headCoachName = GetInput.getUserInput("Please enter HeadCoach Name !");
        }

        /*
         * Add logic to show team creation
         *
         * */
    }

    private boolean isTeamPresent(String teamName)  {
        boolean isTeamPresent = false;
        TeamConcrete teamConcrete = new TeamConcrete();
        ITeamFactory factory = teamConcrete.newLoadTeamFactory();
        Team team = null;
        try {
            team = teamConcrete.newTeamByName(teamName, factory);
        }catch (Exception e) {
            System.out.println("Unable to load team, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if(team!=null && team.getId() > 0) {
            isTeamPresent = true;
        }
        return isTeamPresent;
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
/*
* team.setPlayer();
*
* */
                        division.getTeamList().add(team);
                    }
                }
                conference.setDivisionList(divisionList);
            }
        }
        league.setConferenceList(conferenceList);
        hockeyContext.getUser().setLeague(league);
    }

    @Override
    public IHockeyState exit() {

        System.out.println("Please wait while we are saving your league information...");

        IHockeyState hockeyState = null;
        if (league != null) {
            //Persist to DB and transition to next state
            league.setCreatedBy(hockeyContext.getUser().getId());
            Season season = new Season();
            season.setName(seasonName);

            try {
                LeagueConcrete leagueConcrete = new LeagueConcrete();
                ILeagueFactory addLeagueFactory = leagueConcrete.newAddLeagueFactory();
                league.addLeague(addLeagueFactory);
                int leagueId = league.getId();

                SeasonConcrete seasonConcrete = new SeasonConcrete();
                ISeasonFactory addSeasonDao = seasonConcrete.newAddSeasonFactory();
                season.addSeason(addSeasonDao);
                int seasonId = season.getId();

                if(leagueId != 0 && seasonId != 0){
                    if(league.getFreeAgent() != null) {
                        int freeAgentId = addFreeAgent(leagueId, seasonId);
                        addPlayerList(0,freeAgentId, seasonId, league.getFreeAgent().getPlayerList());
                    }

                    if(league.getConferenceList() != null && !league.getConferenceList().isEmpty()){
                        ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
                        IConferenceFactory addConferenceDao = conferenceConcrete.newAddConferenceFactory();
                        for (Conference conference : league.getConferenceList()) {
                            conference.setLeagueId(leagueId);
                            conference.addConference(addConferenceDao);
                            int conferenceId = conference.getId();

                            for (Division division : conference.getDivisionList()) {
                                DivisionConcrete divisionConcrete = new DivisionConcrete();
                                IDivisionFactory addDivisionDao = divisionConcrete.newAddDivisionFactory();

                                division.setConferenceId(conferenceId);
                                division.addDivision(addDivisionDao);
                                int divisionId = division.getId();

                                for (Team team : division.getTeamList()) {
                                    TeamConcrete teamConcrete = new TeamConcrete();
                                    ITeamFactory addTeamDao = teamConcrete.newAddTeamFactory();

                                    team.setDivisionId(divisionId);
                                    team.addTeam(addTeamDao);
                                    int teamId = team.getId();
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

            String createAnotherTeam = GetInput.getUserInput("Do you want to create another team? Yes/Y or No/N");
            while(createAnotherTeam !=null){

                if(createAnotherTeam.toLowerCase().equals("y") || createAnotherTeam.toLowerCase().equals("yes")  ){
                    hockeyState = new CreateTeamState(hockeyContext);
                    break;
                }else if(createAnotherTeam.toLowerCase().equals("n") || createAnotherTeam.toLowerCase().equals("no")){
                    hockeyState = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "createOrLoadTeam");
                    break;
                }else{
                    System.out.println("Please enter the right choice. Yes/Y or No/N");
                }
            }
        }
        return hockeyState;
    }

    private int addFreeAgent(int leagueId, int seasonId) throws Exception {
        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();
        IFreeAgentFactory freeAgentDao = freeAgentConcrete.newAddFreeAgentFactory();
        FreeAgent freeAgent = league.getFreeAgent();
        freeAgent.setSeasonId(seasonId);
        freeAgent.setLeagueId(leagueId);
        freeAgent.addFreeAgent(freeAgentDao);
        return freeAgent.getId();
    }

    private void addPlayerList(int teamId, int freeAgentId, int seasonId, List<Player> playerList) throws Exception {
        if(playerList != null && !playerList.isEmpty()) {
            PlayerConcrete playerConcrete = new PlayerConcrete();
            IPlayerFactory addPlayerDao = playerConcrete.newAddPlayerFactory();
            for (Player player : playerList) {
                player.setTeamId(teamId);
                player.setFreeAgentId(freeAgentId);
                player.setSeasonId(seasonId);
                addPlayerDao.addPlayer(player);
            }
        }
    }
}