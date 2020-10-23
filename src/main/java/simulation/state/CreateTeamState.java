package simulation.state;

import db.data.*;
import simulation.factory.*;
import userIO.ConsoleOutput;
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
    private Manager generalManager;
    private Coach headCoach;
    private String seasonName;
    private List<Player> teamPlayersList;

    public CreateTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public void entry() {

        if(isLeaguePresent(league.getName())){
            ConsoleOutput.printToConsole("League already exists. Please enter a new one");
            System.out.println();
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

        divisionName = GetInput.getUserInput("Please enter division name the team belongs to");

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

        Team team = new Team();
        teamName  = GetInput.getUserInput("Please enter a team name to create a team ");

        while(teamNameList.contains(teamName.toLowerCase()) || teamName.isEmpty() || teamName ==null || isTeamPresent(teamName)){

            if(teamNameList.contains(teamName.toLowerCase())){
                teamName  = GetInput.getUserInput("Provided team name  already exists. Please enter a new one!");
            }
            else if(teamName.isEmpty() || teamName ==null || isTeamPresent(teamName)){
                teamName = GetInput.getUserInput("Please enter valid team name! Make sure there is no existing team with provided name");
            }
        }

        team.setName(teamName);

        List<Manager> managerList = league.getManagerList();
        ConsoleOutput.printToConsole("Manager List \n ___________ ");
        for(int i=0;i<managerList.size();i++){
            ConsoleOutput.printToConsole("Manager id: "+i);
            ConsoleOutput.printToConsole("Name of Manager: "+managerList.get(i).getName());
        }

        int generalManagerId = Integer.parseInt(GetInput.getUserInput("Please enter id of general manager"));
        while(generalManagerId<0 || (generalManagerId >managerList.size()-1)){
            generalManagerId = Integer.parseInt(GetInput.getUserInput("Please enter GeneralManager id between 0 to "+ (managerList.size()-1)+". (boundaries inclusive)"));
        }
        generalManager = new Manager(managerList.get(generalManagerId));
        team.setManager(generalManager);
        managerList=league.removeManagerFromManagerListById(managerList, generalManagerId);
        league.setManagerList(managerList);

        ConsoleOutput.printToConsole("General manager added for this team");


        List<Coach> coachList = league.getCoachList();
        ConsoleOutput.printToConsole("Coach List \n ___________ ");
        for(int i=0;i<coachList.size();i++){
            Coach currentCoach = coachList.get(i);
            currentCoach.printCoach(i);
        }
        int headCoachId  = Integer.parseInt(GetInput.getUserInput("Please enter the id of head coach"));

        while(headCoachId<0 || (headCoachId >coachList.size()-1)){
            headCoachId = Integer.parseInt(GetInput.getUserInput("Please enter HeadCoach id between 0 to "+(coachList.size()-1)+". (boundaries inclusive)"));
        }

        headCoach = new Coach(coachList.get(headCoachId));
        team.setCoach(headCoach);
        coachList=league.removeCoachFromCoachListById(coachList,headCoachId);
        league.setCoachList(coachList);

        ConsoleOutput.printToConsole("Head coach added for this team");

        FreeAgent freeAgent = league.getFreeAgent();
        List<Player> freeAgentList=freeAgent.getPlayerList();
        int numberOfSkaters=0;
        int numberOfGoalies=0;

        List<Integer> totalOfSkillsList = new ArrayList<>();
        for(int i=0;i<freeAgentList.size();i++){
            Player freeAgentPlayer = freeAgentList.get(i);
            totalOfSkillsList.add( new Integer (freeAgentPlayer.getSkating()+freeAgentPlayer.getSaving()+freeAgentPlayer.getShooting()+freeAgentPlayer.getChecking()));
         }

        List<Integer> goodFreeAgentsIdList = freeAgent.getGoodFreeAgentsList(totalOfSkillsList);

        ConsoleOutput.printToConsole("Free agent list \n ___________ \n Please add ids of free agents separated by new line whom you want to add to your team");
        ConsoleOutput.printToConsole("Below you can see good players separated from below-average players!");
        ConsoleOutput.printToConsole("You need to choose 18 skaters (forwards and defense) and 2 goalies to complete the team formation process! \n \n");
        ConsoleOutput.printToConsole("Good free agent list :) \n ___________________ ");

        for(int i=0;i<freeAgentList.size();i++){
            Player player=freeAgentList.get(i);
            if(goodFreeAgentsIdList.contains(i)){
                player.printPlayer(i);
            }
        }
        ConsoleOutput.printToConsole("Below-average free agent list :| \n __________________ ");

        for(int i=0;i<freeAgentList.size();i++){
            Player player=freeAgentList.get(i);
            if(!goodFreeAgentsIdList.contains(i)){
                player.printPlayer(i);
            }
        }

        List<Integer> chosenPlayersIdList = new ArrayList<>();
        int playerId;
        while(numberOfGoalies!=2 || numberOfSkaters!=18){
            playerId = Integer.parseInt(GetInput.getUserInput("Please enter id between 0 to "+(freeAgentList.size()-1)+". (boundaries inclusive)"));
            if(playerId<0 || playerId>=freeAgentList.size() || chosenPlayersIdList.contains(playerId)){
                continue;
            }
            Player player = freeAgentList.get(playerId);
            if(numberOfGoalies!=2 && player.getPosition().equals("goalie")){
                chosenPlayersIdList.add(playerId);
                numberOfGoalies=numberOfGoalies+1;
                ConsoleOutput.printToConsole("Team needs more "+(2-numberOfGoalies)+" goalies and "+(18-numberOfSkaters)+" skaters");
            }else if(numberOfSkaters!=18 && (player.getPosition().equals("defense") || player.getPosition().equals("forward"))){
                chosenPlayersIdList.add(playerId);
                numberOfSkaters=numberOfSkaters+1;
                ConsoleOutput.printToConsole("Team needs more "+(2-numberOfGoalies)+" goalies and "+(18-numberOfSkaters)+" skaters");
            }
        }
        ConsoleOutput.printToConsole("\n\nPlease wait your team is getting created...");
        List<Player> teamPlayers = createPlayerListByChosenPlayerId(chosenPlayersIdList, freeAgentList);
        freeAgentList=removeChosenPlayersFromFreeAgentList(chosenPlayersIdList, freeAgentList);
        freeAgent.setPlayerList(freeAgentList);
        team.setPlayerList(teamPlayers);
        league.setFreeAgent(freeAgent);
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
                newFreeAgentList.add(new Player(freeAgentList.get(i)));
            }
        }
        return newFreeAgentList;
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

//                        Coach coach = new Coach();
//                        coach.setName(headCoachName);
//                        team.setCoach(coach);
//                        team.setManager(manager);
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