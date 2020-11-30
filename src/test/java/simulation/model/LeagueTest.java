package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.*;
import simulation.mock.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeagueTest {

    private static ILeagueDao leagueDao;
    private static IModelFactory modelFactory;
    private static IDaoFactory daoFactory;

    @BeforeClass
    public static void setFactoryObj() throws Exception {
        daoFactory = DaoFactoryMock.getInstance();
        leagueDao = daoFactory.createLeagueDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void defaultConstructorTest() {
        ILeague league = modelFactory.createLeague();
        assertNotEquals(league.getId(), 0);
    }

    @Test
    public void leagueTest() {
        ILeague league = modelFactory.createLeagueWithId(1);
        assertEquals(league.getId(), 1);
    }

    @Test
    public void leagueFactoryTest() throws Exception {
        ILeague league = modelFactory.createLeagueWithIdDao(1, leagueDao);
        assertEquals(league.getId(), 1);
        assertEquals(league.getName(), "League1");

        league = modelFactory.createLeagueWithIdDao(2, leagueDao);
        assertNull(league.getName());
    }

    @Test
    public void getConferenceListTest() throws Exception {
        ILeague league = modelFactory.createLeagueWithIdDao(1, leagueDao);
        List<IConference> conferenceList = league.getConferenceList();
        assertNotNull(conferenceList);

        assertTrue(conferenceList.get(0).getId() == (1));
        assertTrue(conferenceList.get(1).getId() == (2));
        assertTrue(conferenceList.get(0).getName().equals("Conference1"));
        assertNull(conferenceList.get(1).getName());
    }

    @Test
    public void setConferenceListTest() throws Exception {
        IConferenceDao conferenceDao = daoFactory.createConferenceDao();
        List<IConference> conferenceList = new ArrayList<>();
        IConference conference = modelFactory.createConferenceWithIdDao(1, conferenceDao);
        conferenceList.add(conference);
        conference = modelFactory.createConferenceWithIdDao(2, conferenceDao);
        conferenceList.add(conference);

        ILeague league = modelFactory.createLeague();
        league.setConferenceList(conferenceList);

        assertTrue(league.getConferenceList().get(0).getId() == (1));
        assertTrue(league.getConferenceList().get(1).getId() == (2));
        assertTrue(league.getConferenceList().get(0).getName().equals("Conference1"));
        assertNull(league.getConferenceList().get(1).getName());
    }

    @Test
    public void removeManagerFromManagerListByIdTest() throws Exception {
        League league = new League(1, leagueDao);
        List<IManager> managerList = league.getManagerList();
        assertEquals(managerList.size(), league.removeManagerFromManagerListById(managerList, 0).size() + 1);
        assertNotEquals(league.removeManagerFromManagerListById(managerList, 0), null);
        assertNotEquals(managerList.size(), league.removeManagerFromManagerListById(managerList, 0).size());
    }

    @Test
    public void removeCoachFromManagerListByIdTest() throws Exception {
        League league = new League(1, leagueDao);
        IModelFactory coachFactory = ModelFactory.getInstance();
        List<ICoach> coachList = league.getCoachList();
        assertEquals(coachList.size(), league.removeCoachFromCoachListById(
                coachList, 0, coachFactory).size() + 1);
        assertNotEquals(league.removeCoachFromCoachListById(
                coachList, 0, coachFactory), null);
        assertNotEquals(coachList.size(), league.removeCoachFromCoachListById(
                coachList, 0, coachFactory).size());
    }

    @Test
    public void createConferenceNameListTest() throws Exception {
        League league = new League(4, leagueDao);
        assertEquals(league.createConferenceNameList().size(), league.getConferenceList().size());
        assertFalse(league.createConferenceNameList().size() > league.getConferenceList().size());
        assertFalse(league.createConferenceNameList().size() < league.getConferenceList().size());

    }

    @Test
    public void getConferenceFromListByNameTest() throws Exception {
        League league = new League(4, leagueDao);
        IConference conference = league.getConferenceFromListByName("Conference4");
        assertEquals(conference.getName(), "Conference4");
        assertNotEquals(conference.getName(), null);
    }

    @Test
    public void getTeamByTeamNameTest() throws Exception {
        League league = new League(4, leagueDao);
        ITeam team = league.getTeamByTeamName("Team1");
        assertEquals(team.getName(), "Team1");
        assertNotEquals(team.getName(), null);
    }

    @Test
    public void getFreeAgentTest() throws Exception {
        ILeague league = modelFactory.createLeagueWithIdDao(1, leagueDao);
        assertNotEquals(league.getFreeAgent().getId(), 0);
        List<IPlayer> playerList = league.getFreeAgent().getPlayerList();
        assertTrue(playerList.get(0).getName().equals("Player1"));
    }

    @Test
    public void setFreeAgentTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.createFreeAgent();
        ILeague league = modelFactory.createLeague();
        IPlayerDao playerFactory = daoFactory.createPlayerDao();
        List<IPlayer> playerList = new ArrayList<>();

        IPlayer player = modelFactory.createPlayerWithIdDao(1, playerFactory);
        playerList.add(player);

        player = modelFactory.createPlayerWithIdDao(2, playerFactory);
        playerList.add(player);

        freeAgent.setId(1);
        freeAgent.setPlayerList(playerList);

        league.setFreeAgent(freeAgent);

        assertTrue(league.getFreeAgent().getId() == 1);
        assertTrue(league.getFreeAgent().getPlayerList().get(0).getId() == 1);
    }

    @Test
    public void addLeagueTest() throws Exception {
        League league = new League();
        league.setId(1);
        league.setName("League1");
        league.addLeague(leagueDao);
        assertTrue(1 == league.getId());
        assertTrue("League1".equals(league.getName()));
    }

    @Test
    public void loadConferenceListByLeagueId() throws Exception {
        IConferenceDao conferenceFactory = new ConferenceMock();
        League league = new League();
        league.loadConferenceListByLeagueId(conferenceFactory);

        assertTrue(league.getConferenceList().get(0).getId() == (1));
        assertTrue(league.getConferenceList().get(1).getId() == (2));
        assertTrue(league.getConferenceList().get(0).getName().equals("Conference1"));
        assertNull(league.getConferenceList().get(1).getName());
    }

    @Test
    public void loadFreeAgentByLeagueId() throws Exception {
        League league = new League(1);
        IFreeAgentDao loadFreeAgentFactory = new FreeAgentMock();
        league.loadFreeAgentByLeagueId(loadFreeAgentFactory);
        assertTrue(league.getFreeAgent().getLeagueId() == league.getId());
    }


    @Test
    public void getTradingOfferListTest() throws Exception {
        ILeague league = modelFactory.createLeagueWithIdDao(1, leagueDao);
        ITradeOfferDao tradeOfferFactory = daoFactory.createTradeOfferDao();
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferFactory);
        assertEquals(league.getTradeOfferList().get(0).getId(), tradeOffer.getId());
        assertEquals(league.getTradeOfferList().get(0).getFromTeamId(), tradeOffer.getFromTeamId());
    }

    @Test
    public void setTradingOfferListTest() throws Exception {
        ITradeOfferDao tradeOfferDao = daoFactory.createTradeOfferDao();
        ITradeOffer tradeOffer = modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao);
        ITradeOffer tradeOffer1 = modelFactory.createTradeOfferWithIdDao(2, tradeOfferDao);
        List<ITradeOffer> tradeOfferList = new ArrayList<>();
        tradeOfferList.add(tradeOffer);
        tradeOfferList.add(tradeOffer1);
        ILeague league = modelFactory.createLeagueWithIdDao(1, leagueDao);
        league.setTradeOfferList(tradeOfferList);
        assertEquals(league.getTradeOfferList().get(0).getId(), tradeOffer.getId());
        assertEquals(league.getTradeOfferList().get(1).getId(), tradeOffer1.getId());
        assertNotEquals(league.getTradeOfferList().get(1).getId(), tradeOffer.getId());
        assertNotEquals(league.getTradeOfferList().get(0).getId(), tradeOffer1.getId());
    }

    @Test
    public void loadTradingOfferDetailsByLeagueId() throws Exception {
        ILeague league = modelFactory.createLeague();
        league.setId(1);
        ITradeOfferDao tradeOfferDao = daoFactory.createTradeOfferDao();
        league.loadTradingOfferDetailsByLeagueId(tradeOfferDao);
        assertEquals(league.getTradeOfferList().get(0).getId(), 1);
        assertNotEquals(league.getTradeOfferList().get(1).getId(), 1);
    }

}
