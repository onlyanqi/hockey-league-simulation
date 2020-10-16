package simulation.state;

import factory.*;
import factory.ConferenceConcrete;
import factory.DivisionConcrete;
import factory.FreeAgentConcrete;
import factory.LeagueConcrete;
import factory.PlayerConcrete;
import factory.TeamConcrete;
import simulation.model.*;
import userIO.GetInput;

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
        simulation.data.ILoadLeagueFactory iLoadLeagueFactory = leagueConcrete.newLoadLeagueFactory();

        hockeyContext.getUser().loadLeagueByUserId(iLoadLeagueFactory);

        if(hockeyContext.getUser().getLeagueList().size()==0){
            System.out.println("You do not have any league, Please create it.");
            System.exit(1);
        }
        league = hockeyContext.getUser().getLeagueList().get(0);

        Division div = null;

        ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
        simulation.data.ILoadConferenceFactory iLoadConferenceFactory = conferenceConcrete.newLoadConferenceFactory();
        league.loadConferenceListByLeagueId(iLoadConferenceFactory);
        List<Conference> conferenceList = league.getConferenceList();
        for(Conference conference: conferenceList){
            DivisionConcrete divisionConcrete = new DivisionConcrete();
            simulation.data.ILoadDivisionFactory iLoadDivisionFactory = divisionConcrete.newLoadDivisionFactory();
            conference.loadDivisionListByConferenceId(iLoadDivisionFactory);
            List<Division> divisionList = conference.getDivisionList();
            for(Division division: divisionList){
                TeamConcrete teamConcrete = new TeamConcrete();
                simulation.data.ILoadTeamFactory iLoadTeamFactory = teamConcrete.newLoadTeamFactory();
                division.loadTeamListByDivisionId(iLoadTeamFactory);

                List<Team> teamArrayList = division.getTeamList();
                for(Team team: teamArrayList){

                    if(teamName != null && team.getName() != null && team.getName().equals(teamName)){
                        div = division;
                    }

                    factory.PlayerConcrete playerConcrete = new PlayerConcrete();
                    simulation.data.ILoadPlayerFactory iLoadPlayerFactory = playerConcrete.newLoadPlayerFactory();
                    team.loadPlayerListByTeamId(iLoadPlayerFactory);
                    team.getPlayerList();
                }
            }
        }

        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();

        simulation.data.ILoadFreeAgentFactory iLoadFreeAgentFactory = freeAgentConcrete.newLoadFreeAgentFactory();
        league.loadFreeAgentByLeagueId(iLoadFreeAgentFactory);
        FreeAgent freeAgent = league.getFreeAgent();

        PlayerConcrete playerConcrete = new PlayerConcrete();
        simulation.data.ILoadPlayerFactory iLoadPlayerFactory = playerConcrete.newLoadPlayerFactory();
        freeAgent.loadPlayerListByFreeAgentId(iLoadPlayerFactory);

        Conference conf = null;
        if(league.getConferenceList() != null && !league.getConferenceList().isEmpty()) {
            for (Conference conference : league.getConferenceList()) {
                if(conference.getDivisionList() != null && !conference.getDivisionList().isEmpty()) {
                    for (Division division : conference.getDivisionList()) {
                        if(division.getName() != null && div.getName() != null &&
                                division.getName().equals(div.getName())){
                            conf = conference;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("The team belongs to \""+league.getName()+"\" league.");
        System.out.println("The team belongs to \""+conf.getName()+"\" conference.");
        System.out.println("The team belongs to \""+div.getName()+"\" division.");

    }

    @Override
    public IHockeyState exit() {
        //Instantiate Model Objects and transition state

        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext,"How many seasons do you want to simulate","createOrLoadTeam");
        return playerChoiceState;
    }

    private boolean isTeamNotPresent(String teamName) throws Exception {
        TeamConcrete teamConcrete = new TeamConcrete();
        simulation.data.ILoadTeamFactory factory = teamConcrete.newLoadTeamFactory();
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
