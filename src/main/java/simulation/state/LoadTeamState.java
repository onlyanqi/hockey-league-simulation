package simulation.state;

import db.data.*;
import presentation.ConsoleOutput;
import presentation.ReadUserInput;
import simulation.factory.*;
import simulation.model.*;
import validator.IValidation;

import java.util.List;


public class LoadTeamState implements IHockeyState {

    private HockeyContext hockeyContext;
    private String teamName;
    private League league;
    private ReadUserInput readUserInput;
    private IValidation iValidation;

    public LoadTeamState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        readUserInput = ReadUserInput.getInstance();
        ValidationConcrete validationConcrete = new ValidationConcrete();
        iValidation = validationConcrete.newValidation();
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public void entry() throws Exception {
        teamName = readUserInput.getInput("Please enter team name");
        while ((teamName.isEmpty() || teamName == null || isTeamNotPresent(teamName))) {
            teamName = readUserInput.getInput("Please enter valid and existing team name");
        }
    }


    @Override
    public void process() throws Exception {
        ConsoleOutput.getInstance().printMsgToConsole("We are loading the league data. Please wait..");

        LeagueConcrete leagueConcrete = new LeagueConcrete();
        ILeagueFactory iLoadLeagueFactory = leagueConcrete.newLoadLeagueFactory();

        hockeyContext.getUser().loadLeagueByUserId(iLoadLeagueFactory);

        if (hockeyContext.getUser().getLeagueList().size() == 0) {
            ConsoleOutput.getInstance().printMsgToConsole("You do not have any league, Please create it.");
            System.exit(1);
        }
        league = hockeyContext.getUser().getLeagueList().get(0);

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
                    if (iValidation.isNotNull(teamName) && iValidation.isNotNull(team.getName()) && team.getName().equals(teamName)) {
                        div = division;
                    }
                    PlayerConcrete playerConcrete = new PlayerConcrete();
                    IPlayerFactory iLoadPlayerFactory = playerConcrete.newPlayerFactory();
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
        IPlayerFactory iLoadPlayerFactory = playerConcrete.newPlayerFactory();
        freeAgent.loadPlayerListByFreeAgentId(iLoadPlayerFactory);

        setGames();
        setCoaches();
        Conference conf = null;
        if (iValidation.isNotNull(league.getConferenceList()) && !league.getConferenceList().isEmpty()) {
            for (Conference conference : league.getConferenceList()) {
                if (iValidation.isNotNull(conference.getDivisionList()) && !conference.getDivisionList().isEmpty()) {
                    for (Division division : conference.getDivisionList()) {
                        if (iValidation.isNotNull(division.getName()) && iValidation.isNotNull(div.getName()) &&
                                division.getName().equals(div.getName())) {
                            conf = conference;
                            break;
                        }
                    }
                }
            }
        }

        ConsoleOutput.getInstance().printMsgToConsole("The team belongs to \"" + league.getName() + "\" league.");
        ConsoleOutput.getInstance().printMsgToConsole("The team belongs to \"" + conf.getName() + "\" conference.");
        ConsoleOutput.getInstance().printMsgToConsole("The team belongs to \"" + div.getName() + "\" division.");

    }

    private void setGames() throws Exception {
        GameConcrete gameConcrete = new GameConcrete();
        IGameFactory gameFactory = gameConcrete.newAddGamesFactory();
        GameSchedule games = new GameSchedule();
        games.setGameList(gameFactory.loadGamesByLeagueId(league.getId()));
        league.setGames(games);
    }

    private void setCoaches() throws Exception {
        CoachConcrete coachConcrete = new CoachConcrete();
        ICoachFactory iCoachFactory = coachConcrete.newCoachFactory();
        List<Coach> coachList = iCoachFactory.loadFreeCoachListByLeagueId(league.getId());
        league.setCoachList(coachList);
    }

    public void updateTradingDetailsToLeague(ITradingFactory tradingFactory) throws Exception {
        league.getGamePlayConfig().setTrading(tradingFactory.loadTradingDetailsByLeagueId(league.getId()));
    }

    @Override
    public IHockeyState exit() {
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
            ConsoleOutput.getInstance().printMsgToConsole("Unable to load team, please try again.");
            System.exit(1);
            e.printStackTrace();
        }
        if (iValidation.isNotNull(team) && team.getId() > 0) {
            return false;
        } else {
            return true;
        }

    }
}
