package simulation.state;

import config.AppConfig;
import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.*;
import simulation.model.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamStateTest {

    static User user;
    static League league;

    private static ILeagueFactory factory = new LeagueMock();
    private static ITeamFactory factoryTeam = new TeamMock();
    private static IUserFactory factoryUser = new UserMock();
    private static HockeyContext hockeyContext = new HockeyContext();
    private List<Manager> managerList = null;
    private List<Coach> coachList = null;
    private FreeAgent freeAgent = null;
    private String conferenceName = null;
    private String divisionName = null;
    private Team team = null;

    @BeforeClass
    public static void setState() throws Exception {
        factory = new LeagueMock();
        league = new League(4,factory);
        user = new User(1,factoryUser);
        user.setId(1);
        hockeyContext.setUser(user);
    }

    @Test
    public void entryTest() throws Exception {
         chooseConferenceTest();
         chooseCoachTest();
         chooseDivisionTest();
         chooseManagerTest();
         choosePlayersTest();
    }

    @Test
    public void hasEnoughManagersTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        List<Manager> managerList;
        IManagerFactory managerFactory = new ManagerMock();
        managerList = managerFactory.loadFreeManagersByLeagueId(1);
        assertTrue(createTeamState.hasEnoughManagers(managerList));
        assertFalse(createTeamState.hasEnoughManagers(null));
        int managerListSize = managerList.size();
        for (int i=managerListSize-1;i>=0;i--){
            managerList.remove(i);
        }
        assertFalse(createTeamState.hasEnoughManagers(managerList));
    }

    @Test
    public void hasEnoughCoachTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        List<Coach> coachList;
        ICoachFactory coachFactory = new CoachMock();
        coachList = coachFactory.loadFreeCoachListByLeagueId(1);
        assertTrue(createTeamState.hasEnoughCoaches(coachList));
        assertFalse(createTeamState.hasEnoughCoaches(null));
        int coachListSize = coachList.size();
        for (int i=coachListSize-1;i>=0;i--){
            coachList.remove(i);
        }
        assertFalse(createTeamState.hasEnoughCoaches(coachList));

    }

    @Test
    public void hasEnoughFreeAgentTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        IFreeAgentFactory freeAgentFactory= new FreeAgentMock();
        FreeAgent freeAgent = new FreeAgent(1,freeAgentFactory);
        assertTrue(createTeamState.hasEnoughFreeAgent(freeAgent));
        freeAgent = new FreeAgent(5,freeAgentFactory);
        assertTrue(createTeamState.hasEnoughFreeAgent(freeAgent));
        assertFalse(createTeamState.hasEnoughFreeAgent(null));
        List<Player> freeAgentList = freeAgent.getPlayerList();
        int freeAgentListSize = freeAgentList.size();
        for (int i=freeAgentListSize-1;i>=0;i--){
            freeAgentList.remove(i);
        }
        assertFalse(createTeamState.hasEnoughFreeAgent(freeAgent));
    }

    @Test
    public void getTeamNameTest() throws Exception {
        IDivisionFactory divisionFactory = new DivisionMock();
        Division division = new Division(4,divisionFactory);
        List<String> teamNameList = division.getTeamNameList();
        String teamName = "team10";
        assertTrue(teamName,!teamNameList.contains(teamName));
        teamName = "Team1";
        assertFalse(teamName,teamNameList.contains(teamName));
    }

    @Test
    public void chooseDivisionTest() throws Exception {
        IConferenceFactory conferenceFactory = new ConferenceMock();
        Conference conference = new Conference(4,conferenceFactory);
        List<String> divisionNameList = conference.getDivisionNameList();
        String divisionName = "Division4";
        assertTrue(divisionName,divisionNameList.contains(divisionName.toLowerCase()));
        divisionName = "Divisio10";
        assertFalse(divisionName,divisionNameList.contains(divisionName.toLowerCase()));
    }

    @Test
    public void chooseConferenceTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(4,leagueFactory);
        List<String> conferenceNameList = league.createConferenceNameList();
        String conferenceName = "Conference4";
        assertTrue(conferenceName,conferenceNameList.contains(conferenceName.toLowerCase()));
        conferenceName = "Conference5";
        assertFalse(conferenceName,conferenceNameList.contains(conferenceName.toLowerCase()));
    }

    @Test
    public void choosePlayersTest() throws Exception{
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(4,leagueFactory);
        List<Player> freeAgentList  = league.getFreeAgent().getPlayerList();
        int oldSizeOfFreeAgentList = freeAgentList.size();
        List<Integer> chosenPlayersIdList = new ArrayList<>();
        for(int i=1;i<21;i++){
            chosenPlayersIdList.add(i);
        }
        CreateTeamState createTeamState = new CreateTeamState();
        List<Player> teamPlayers =  createTeamState.createPlayerListByChosenPlayerId(chosenPlayersIdList,freeAgentList);
        int teamPlayersListLength = teamPlayers.size();
        freeAgentList = createTeamState.removeChosenPlayersFromFreeAgentList(chosenPlayersIdList, freeAgentList);
        int newSizeOfFreeAgentList = freeAgentList.size();
        assertTrue(newSizeOfFreeAgentList==oldSizeOfFreeAgentList-teamPlayersListLength);
        newSizeOfFreeAgentList++;
        assertFalse(newSizeOfFreeAgentList==oldSizeOfFreeAgentList-teamPlayersListLength);
    }

    @Test
    public void chooseCoachTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(4,leagueFactory);
        List<Coach> coachList = league.getCoachList();
        int oldCoachListLength = coachList.size();
        coachList = league.removeCoachFromCoachListById(coachList, 1);
        int newCoachListLength = coachList.size();
        assertTrue(oldCoachListLength==newCoachListLength+1);
        newCoachListLength = newCoachListLength+1;
        assertFalse(oldCoachListLength==newCoachListLength+1);
    }

    @Test
    public void chooseManagerTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(4,leagueFactory);
        List<Manager> managerList = league.getManagerList();
        int oldManagerListLength = managerList.size();
        managerList = league.removeManagerFromManagerListById(managerList, 1);
        int newManagerListLength = managerList.size();
        assertTrue(oldManagerListLength==newManagerListLength+1);
        newManagerListLength = newManagerListLength+1;
        assertFalse(oldManagerListLength==newManagerListLength+1);
    }

    @Test
    public void createPlayerListByChosenPlayerIdTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        List<Integer> chosenPlayersIdList = new ArrayList<>();
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(4,leagueFactory);
        List<Player> freeAgentList  = league.getFreeAgent().getPlayerList();
        int freeAgentListSize = freeAgentList.size();
        for(int i=1;i<21;i++){
            chosenPlayersIdList.add(i);
        }
        List<Player> teamPlayers = createTeamState.createPlayerListByChosenPlayerId(chosenPlayersIdList,freeAgentList);

        assertEquals(teamPlayers.size(),20);
        assertNotEquals(teamPlayers.size(),15);
        assertNotEquals(teamPlayers.size(),0);
        assertNotEquals(teamPlayers.size(),22);

        teamPlayers = createTeamState.createPlayerListByChosenPlayerId(null,null);
        assertEquals(teamPlayers,null);


    }

    @Test
    public void removeChosenPlayersFromFreeAgentListTest() throws Exception {
        CreateTeamState createTeamState = new CreateTeamState();
        List<Integer> chosenPlayersIdList = new ArrayList<>();
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(4,leagueFactory);
        List<Player> freeAgentList  = league.getFreeAgent().getPlayerList();
        int freeAgentListSize = freeAgentList.size();
        for(int i=1;i<21;i++){
            chosenPlayersIdList.add(i);
        }
        freeAgentList = createTeamState.removeChosenPlayersFromFreeAgentList(chosenPlayersIdList,freeAgentList);

        assertEquals(freeAgentList.size(),freeAgentListSize-20);
        assertNotEquals(freeAgentList.size(),-1);
        assertNotEquals(freeAgentList.size(),freeAgentListSize+1);

        freeAgentList = createTeamState.removeChosenPlayersFromFreeAgentList(null,null);
        assertEquals(freeAgentList,null);

    }

    @Test
    public void isLeaguePresentTest() throws Exception {
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1,leagueFactory);

        assertEquals(league.getName(),"League1");
        assertNotEquals(league.getName(),"League2");
    }

    @Test
    public void processTest() throws Exception {
        hockeyContext.setUser(user);
        managerList=league.getManagerList();
        coachList=league.getCoachList();
        freeAgent=league.getFreeAgent();
        conferenceName="Conference1";
        divisionName="Division1";
        List<Conference> conferenceList = league.getConferenceList();
        conferenceList.remove(1);
        league.setConferenceList(conferenceList);
        List<Division> divisionList = league.getConferenceList().get(0).getDivisionList();
        divisionList.remove(1);
        league.getConferenceList().get(0).setDivisionList(divisionList);
        hockeyContext.getUser().setLeague(league);
        ITeamFactory teamFactory = new TeamMock();
        team = new Team(1,teamFactory);
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext,null,null);
        createTeamState.setConferenceName(conferenceName);
        createTeamState.setDivisionName(divisionName);
        createTeamState.process();
        assertFalse(null==league.getFreeAgent() || null == league.getManagerList() || null == league.getCoachList() || null == league.getConferenceList() );
        assertTrue(league.getConferenceList().size()>0);
    }

    @Test
    public void getConferenceNameTest(){
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext,null,null);
        createTeamState.setConferenceName("conf1");
        assertEquals(createTeamState.getConferenceName(),"conf1");
    }

    @Test
    public void setConferenceNameTest(){
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext,null,null);
        createTeamState.setConferenceName("conf1");
        assertEquals(createTeamState.getConferenceName(),"conf1");
    }

    @Test
    public void setDivisionNameTest(){
        CreateTeamState createTeamState = new CreateTeamState(hockeyContext,null,null);
        createTeamState.setDivisionName("div1");
        assertEquals(createTeamState.getDivisionName(),"div1");
    }

    @Test
    public void exitTest() {
        IHockeyState hockeyState = new CreateTeamState();
        assertFalse(hockeyState == null);
        assertEquals(hockeyState.getClass().toString(),"class simulation.state.CreateTeamState");
    }
}
