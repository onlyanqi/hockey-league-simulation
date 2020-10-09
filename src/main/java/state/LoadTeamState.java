package state;

import data.*;
import factory.*;
import model.*;
import org.icehockey.GetInput;

import java.util.List;


public class LoadTeamState implements IHockeyState {

    private String input;
    private HockeyContext hockeyContext;
    private String teamName;
    private League league;


    public LoadTeamState(HockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
    }

    @Override
    public void entry() throws Exception {
        //prompt team name

        teamName  = GetInput.getUserInput("Please enter team name");

        while((teamName.isEmpty() || teamName ==null || isTeamNotPresent(teamName))){
            teamName = GetInput.getUserInput("Please enter valid and existing team name");
        }

    }


    @Override
    public void process() throws Exception {

        System.out.println("We are loading the league data. Please wait..");

        //Load League from userid
        LeagueConcrete leagueConcrete = new LeagueConcrete();
        ILoadLeagueFactory iLoadLeagueFactory = leagueConcrete.newLoadLeagueFactory();



        hockeyContext.getUser().loadLeagueByUserId(iLoadLeagueFactory);

        if(hockeyContext.getUser().getLeagueList().size()==0){
            System.out.println("You do not have any league, Please create it.");
            System.exit(1);
        }
        league = hockeyContext.getUser().getLeagueList().get(0);

        ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
        ILoadConferenceFactory iLoadConferenceFactory = conferenceConcrete.newLoadConferenceFactory();
        league.loadConferenceListByLeagueId(iLoadConferenceFactory);
        List<Conference> conferenceList = league.getConferenceList();
        for(Conference conference: conferenceList){
            DivisionConcrete divisionConcrete = new DivisionConcrete();
            ILoadDivisionFactory iLoadDivisionFactory = divisionConcrete.newLoadDivisionFactory();
            conference.loadDivisionListByConferenceId(iLoadDivisionFactory);
            List<Division> divisionList = conference.getDivisionList();
            for(Division division: divisionList){
                TeamConcrete teamConcrete = new TeamConcrete();
                ILoadTeamFactory iLoadTeamFactory = teamConcrete.newLoadTeamFactory();
                division.loadTeamListByDivisionId(iLoadTeamFactory);
                List<Team> teamArrayList = division.getTeamList();
                for(Team team: teamArrayList){
                    PlayerConcrete playerConcrete = new PlayerConcrete();
                    ILoadPlayerFactory iLoadPlayerFactory = playerConcrete.newLoadPlayerFactory();
                    team.loadPlayerListByTeamId(iLoadPlayerFactory);
                    List<Player> playerList = team.getPlayerList();
                }
            }
        }

        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();

        ILoadFreeAgentFactory iLoadFreeAgentFactory = freeAgentConcrete.newLoadFreeAgentFactory();
        league.loadFreeAgentByLeagueId(iLoadFreeAgentFactory);
        FreeAgent freeAgent = league.getFreeAgent();

        PlayerConcrete playerConcrete = new PlayerConcrete();
        ILoadPlayerFactory iLoadPlayerFactory = playerConcrete.newLoadPlayerFactory();
        freeAgent.loadPlayerListByFreeAgentId(iLoadPlayerFactory);

    }

    @Override
    public IHockeyState exit() {
        //Instantiate Model Objects and transition state

        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        return playerChoiceState;
    }

    private boolean isTeamNotPresent(String teamName) throws Exception {
        TeamConcrete teamConcrete = new TeamConcrete();
        ILoadTeamFactory factory = teamConcrete.newLoadTeamFactory();
        Team team = null;
        try {
            team = teamConcrete.newTeamByName(teamName, factory);
        }catch (Exception e) {
            System.out.println("Unable to load team, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if(team!=null && team.getId() >0) return false;
        else return true;

    }
}
