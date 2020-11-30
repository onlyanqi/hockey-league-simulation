package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.*;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.*;
import simulation.model.*;

import java.util.List;

import static org.junit.Assert.*;

public class CreateTeamStateTest {

    private static final ITeamDao factoryTeam = new TeamMock();
    private static final IUserDao factoryUser = new UserMock();
    static User user;
    static League league;
    private static ILeagueDao factory = new LeagueMock();
    private static IHockeyContext hockeyContext;
    private static IHockeyContextFactory hockeyContextFactory;
    private List<IManager> managerList = null;
    private List<ICoach> coachList = null;
    private IFreeAgent freeAgent = null;
    private String conferenceName = null;
    private String divisionName = null;
    private ITeam team = null;

    @BeforeClass
    public static void setState() throws Exception {
        factory = new LeagueMock();
        league = new League(4, factory);
        user = new User(1, factoryUser);
        user.setId(1);
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        hockeyContext.setUser(user);
    }


    @Test
    public void hasEnoughManagersTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        List<IManager> managerList;
        IManagerDao managerFactory = new ManagerMock();
        managerList = managerFactory.loadFreeManagersByLeagueId(1);
        assertTrue(createTeamState.hasEnoughManagers(managerList));
        assertFalse(createTeamState.hasEnoughManagers(null));
        int managerListSize = managerList.size();
        for (int i = managerListSize - 1; i >= 0; i--) {
            managerList.remove(i);
        }
        assertFalse(createTeamState.hasEnoughManagers(managerList));
    }

    @Test
    public void hasEnoughCoachTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        List<ICoach> coachList;
        ICoachDao coachFactory = new CoachMock();
        coachList = coachFactory.loadFreeCoachListByLeagueId(1);
        assertTrue(createTeamState.hasEnoughCoaches(coachList));
        assertFalse(createTeamState.hasEnoughCoaches(null));
        int coachListSize = coachList.size();
        for (int i = coachListSize - 1; i >= 0; i--) {
            coachList.remove(i);
        }
        assertFalse(createTeamState.hasEnoughCoaches(coachList));

    }

    @Test
    public void hasEnoughFreeAgentTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        IFreeAgentDao freeAgentFactory = new FreeAgentMock();
        FreeAgent freeAgent = new FreeAgent(1, freeAgentFactory);
        assertTrue(createTeamState.hasEnoughFreeAgent(freeAgent));
        List<IPlayer> freeAgentList = freeAgent.getPlayerList();
        int freeAgentListSize = freeAgentList.size();
        for (int i = freeAgentListSize - 1; i >= 0; i--) {
            freeAgentList.remove(i);
        }
        assertFalse(createTeamState.hasEnoughFreeAgent(freeAgent));
        freeAgent = new FreeAgent(5, freeAgentFactory);
        assertFalse(createTeamState.hasEnoughFreeAgent(freeAgent));
    }

    @Test
    public void getTeamNameTest() throws Exception {
        IDivisionDao divisionFactory = new DivisionMock();
        Division division = new Division(4, divisionFactory);
        List<String> teamNameList = division.getTeamNameList();
        String teamName = "team10";
        assertTrue(teamName, !teamNameList.contains(teamName));
        teamName = "Team1";
        assertFalse(teamName, teamNameList.contains(teamName));
    }

    @Test
    public void chooseDivisionTest() throws Exception {
        IConferenceDao conferenceFactory = new ConferenceMock();
        Conference conference = new Conference(4, conferenceFactory);
        List<String> divisionNameList = conference.getDivisionNameList();
        String divisionName = "Division4";
        assertTrue(divisionName, divisionNameList.contains(divisionName.toLowerCase()));
        divisionName = "Divisio10";
        assertFalse(divisionName, divisionNameList.contains(divisionName.toLowerCase()));
    }

    @Test
    public void chooseConferenceTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(4, leagueFactory);
        List<String> conferenceNameList = league.createConferenceNameList();
        String conferenceName = "Conference4";
        assertTrue(conferenceName, conferenceNameList.contains(conferenceName.toLowerCase()));
        conferenceName = "Conference5";
        assertFalse(conferenceName, conferenceNameList.contains(conferenceName.toLowerCase()));
    }


    @Test
    public void chooseCoachTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(4, leagueFactory);
        IModelFactory coachFactory = ModelFactory.getInstance();
        List<ICoach> coachList = league.getCoachList();
        int oldCoachListLength = coachList.size();
        coachList = league.removeCoachFromCoachListById(coachList, 1, coachFactory);
        int newCoachListLength = coachList.size();
        assertTrue(oldCoachListLength == newCoachListLength + 1);
        newCoachListLength = newCoachListLength + 1;
        assertFalse(oldCoachListLength == newCoachListLength + 1);
    }

    @Test
    public void chooseManagerTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(4, leagueFactory);
        List<IManager> managerList = league.getManagerList();
        int oldManagerListLength = managerList.size();
        managerList = league.removeManagerFromManagerListById(managerList, 1);
        int newManagerListLength = managerList.size();
        assertTrue(oldManagerListLength == newManagerListLength + 1);
        newManagerListLength = newManagerListLength + 1;
        assertFalse(oldManagerListLength == newManagerListLength + 1);
    }


    @Test
    public void isLeaguePresentTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);

        assertEquals(league.getName(), "League1");
        assertNotEquals(league.getName(), "League2");
    }

    @Test
    public void processTest() throws Exception {
        hockeyContext.setUser(user);
        managerList = league.getManagerList();
        coachList = league.getCoachList();
        freeAgent = league.getFreeAgent();
        conferenceName = "Conference1";
        divisionName = "Division1";
        List<IConference> conferenceList = league.getConferenceList();
        conferenceList.remove(1);
        league.setConferenceList(conferenceList);
        List<IDivision> divisionList = league.getConferenceList().get(0).getDivisionList();
        divisionList.remove(1);
        league.getConferenceList().get(0).setDivisionList(divisionList);
        hockeyContext.getUser().setLeague(league);
        ITeamDao teamFactory = new TeamMock();
        team = new Team(1, teamFactory);
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext, null, null);
        createTeamState.setConferenceName(conferenceName);
        createTeamState.setDivisionName(divisionName);
        createTeamState.process();
        assertTrue(league.getConferenceList().size() > 0);
    }

    @Test
    public void getConferenceNameTest() {
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext, null, null);
        createTeamState.setConferenceName("conf1");
        assertEquals(createTeamState.getConferenceName(), "conf1");
    }

    @Test
    public void setConferenceNameTest() {
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext, null, null);
        createTeamState.setConferenceName("conf1");
        assertEquals(createTeamState.getConferenceName(), "conf1");
    }

    @Test
    public void setDivisionNameTest() {
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext, null, null);
        createTeamState.setDivisionName("div1");
        assertEquals(createTeamState.getDivisionName(), "div1");
    }

    @Test
    public void exitTest() {
        IHockeyState hockeyState = new CreateTeamState();
        assertFalse(hockeyState == null);
        assertEquals(hockeyState.getClass().toString(), "class simulation.state.CreateTeamState");
    }
}
