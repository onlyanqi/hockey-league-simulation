package simulation.state;

import db.data.*;
import presentation.ReadUserInput;
import simulation.factory.*;
import simulation.model.*;
import java.util.List;


public class LoadTeamState implements IHockeyState {

    private HockeyContext hockeyContext;
    private String teamName;
    private League league;
    private ReadUserInput readUserInput;

    public LoadTeamState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        readUserInput = ReadUserInput.getInstance();
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public void entry() throws Exception {
        //prompt team name

        teamName = readUserInput.getInput("Please enter team name");

        while ((teamName.isEmpty() || teamName == null || isTeamNotPresent(teamName))) {
            teamName = readUserInput.getInput("Please enter valid and existing team name");
        }

    }


    @Override
    public void process() throws Exception {

        System.out.println("We are loading the league data. Please wait..");

        //Load League from userid
        LeagueConcrete leagueConcrete = new LeagueConcrete();
        ILeagueFactory iLoadLeagueFactory = leagueConcrete.newLoadLeagueFactory();

        hockeyContext.getUser().loadLeagueByUserId(iLoadLeagueFactory);

        if (hockeyContext.getUser().getLeagueList().size() == 0) {
            System.out.println("You do not have any league, Please create it.");
            System.exit(1);
        }
        league = hockeyContext.getUser().getLeagueList().get(0);

        TradingConcrete tradingConcrete = new TradingConcrete();
        ITradingFactory tradingFactory = tradingConcrete.newTradingFactory();
        updateTradingDetailsToLeague(tradingFactory);

        Division div = null;

        ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
        IConferenceFactory iLoadConferenceFactory = conferenceConcrete.newLoadConferenceFactory();
        league.loadConferenceListByLeagueId(iLoadConferenceFactory);
        List<Conference> conferenceList = league.getConferenceList();
        for (Conference conference : conferenceList) {
            DivisionConcrete divisionConcrete = new DivisionConcrete();
            IDivisionFactory iLoadDivisionFactory = divisionConcrete.newLoadDivisionFactory();
            conference.loadDivisionListByConferenceId(iLoadDivisionFactory);
            List<Division> divisionList = conference.getDivisionList();
            for (Division division : divisionList) {
                TeamConcrete teamConcrete = new TeamConcrete();
                ITeamFactory iLoadTeamFactory = teamConcrete.newTeamFactory();
                division.loadTeamListByDivisionId(iLoadTeamFactory);

                List<Team> teamArrayList = division.getTeamList();
                for (Team team : teamArrayList) {

                    if (teamName != null && team.getName() != null && team.getName().equals(teamName)) {
                        div = division;
                    }

                    PlayerConcrete playerConcrete = new PlayerConcrete();
                    IPlayerFactory iLoadPlayerFactory = playerConcrete.newLoadPlayerFactory();
                    team.loadPlayerListByTeamId(iLoadPlayerFactory);
                    team.getPlayerList();
                }
            }
        }

        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();

        IFreeAgentFactory iLoadFreeAgentFactory = freeAgentConcrete.newLoadFreeAgentFactory();
        league.loadFreeAgentByLeagueId(iLoadFreeAgentFactory);
        FreeAgent freeAgent = league.getFreeAgent();

        PlayerConcrete playerConcrete = new PlayerConcrete();
        IPlayerFactory iLoadPlayerFactory = playerConcrete.newLoadPlayerFactory();
        freeAgent.loadPlayerListByFreeAgentId(iLoadPlayerFactory);

        Conference conf = null;
        if (league.getConferenceList() != null && !league.getConferenceList().isEmpty()) {
            for (Conference conference : league.getConferenceList()) {
                if (conference.getDivisionList() != null && !conference.getDivisionList().isEmpty()) {
                    for (Division division : conference.getDivisionList()) {
                        if (division.getName() != null && div.getName() != null &&
                                division.getName().equals(div.getName())) {
                            conf = conference;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("The team belongs to \"" + league.getName() + "\" league.");
        System.out.println("The team belongs to \"" + conf.getName() + "\" conference.");
        System.out.println("The team belongs to \"" + div.getName() + "\" division.");

    }

    public void updateTradingDetailsToLeague(ITradingFactory tradingFactory) throws Exception {
        league.getGamePlayConfig().setTrading(tradingFactory.loadTradingDetailsByLeagueId(league.getId()));
    }

    @Override
    public IHockeyState exit() {
        //Instantiate Model Objects and transition state

        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "createOrLoadTeam");
        return playerChoiceState;
    }

    private boolean isTeamNotPresent(String teamName) throws Exception {
        TeamConcrete teamConcrete = new TeamConcrete();
        ITeamFactory factory = teamConcrete.newTeamFactory();
        Team team = null;
        try {
            team = teamConcrete.newTeamByName(teamName, factory);
        } catch (Exception e) {
            System.out.println("Unable to load team, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if (team != null && team.getId() > 0) return false;
        else return true;

    }
}
