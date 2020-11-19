package simulation.model;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeagueTest {

    private static ILeagueFactory leagueFactory;


    @BeforeClass
    public static void setFactoryObj() throws Exception {
        leagueFactory = new LeagueMock();
    }

    @Test
    public void defaultConstructorTest() {
        League league = new League();
        assertNotEquals(league.getId(), 0);
    }

    @Test
    public void leagueTest() {
        League league = new League(1);
        assertEquals(league.getId(), 1);
    }

    @Test
    public void leagueFactoryTest() throws Exception {
        League league = new League(1, leagueFactory);
        assertEquals(league.getId(), 1);
        assertEquals(league.getName(), "League1");

        league = new League(2, leagueFactory);
        assertNull(league.getName());
    }

    @Test
    public void getConferenceListTest() throws Exception {
        League league = new League(1, leagueFactory);
        List<Conference> conferenceList = league.getConferenceList();
        assertNotNull(conferenceList);

        assertTrue(conferenceList.get(0).getId() == (1));
        assertTrue(conferenceList.get(1).getId() == (2));
        assertTrue(conferenceList.get(0).getName().equals("Conference1"));
        assertNull(conferenceList.get(1).getName());
    }

    @Test
    public void setConferenceListTest() throws Exception {
        IConferenceFactory conferenceFactory = new ConferenceMock();
        List<Conference> conferenceList = new ArrayList<>();
        Conference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);
        conference = new Conference(2, conferenceFactory);
        conferenceList.add(conference);

        League league = new League();
        league.setConferenceList(conferenceList);

        assertTrue(league.getConferenceList().get(0).getId() == (1));
        assertTrue(league.getConferenceList().get(1).getId() == (2));
        assertTrue(league.getConferenceList().get(0).getName().equals("Conference1"));
        assertNull(league.getConferenceList().get(1).getName());
    }

    @Test
    public void removeManagerFromManagerListByIdTest() throws Exception {
        League league = new League(1, leagueFactory);
        List<Manager> managerList = league.getManagerList();
        assertEquals(managerList.size(), league.removeManagerFromManagerListById(managerList, 0).size() + 1);
        assertNotEquals(league.removeManagerFromManagerListById(managerList, 0), null);
        assertNotEquals(managerList.size(), league.removeManagerFromManagerListById(managerList, 0).size());
    }

    @Test
    public void removeCoachFromManagerListByIdTest() throws Exception {
        League league = new League(1, leagueFactory);
        List<Coach> coachList = league.getCoachList();
        assertEquals(coachList.size(), league.removeCoachFromCoachListById(coachList, 0).size() + 1);
        assertNotEquals(league.removeCoachFromCoachListById(coachList, 0), null);
        assertNotEquals(coachList.size(), league.removeCoachFromCoachListById(coachList, 0).size());
    }

    @Test
    public void createConferenceNameListTest() throws Exception {
        League league = new League(4, leagueFactory);
        assertEquals(league.createConferenceNameList().size(), league.getConferenceList().size());
        assertFalse(league.createConferenceNameList().size() > league.getConferenceList().size());
        assertFalse(league.createConferenceNameList().size() < league.getConferenceList().size());

    }

    @Test
    public void getConferenceFromListByNameTest() throws Exception {
        League league = new League(4, leagueFactory);
        Conference conference = league.getConferenceFromListByName("Conference4");
        assertEquals(conference.getName(), "Conference4");
        assertNotEquals(conference.getName(), null);
    }

    @Test
    public void getTeamByTeamNameTest() throws Exception {
        League league = new League(4, leagueFactory);
        Team team = league.getTeamByTeamName("Team1");
        assertEquals(team.getName(), "Team1");
        assertNotEquals(team.getName(), null);
    }

    @Test
    public void getFreeAgentTest() throws Exception {
        League league = new League(1, leagueFactory);
        assertNotEquals(league.getFreeAgent().getId(), 0);
        List<Player> playerList = league.getFreeAgent().getPlayerList();
        assertTrue(playerList.get(0).getName().equals("Player1"));
    }

    @Test
    public void setFreeAgentTest() throws Exception {
        FreeAgent freeAgent = new FreeAgent();
        League league = new League();
        IPlayerFactory playerFactory = new PlayerMock();
        List<Player> playerList = new ArrayList<>();

        Player player = new Player(1, playerFactory);
        playerList.add(player);

        player = new Player(2, playerFactory);
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
        league.addLeague(leagueFactory);
        assertTrue(1 == league.getId());
        assertTrue("League1".equals(league.getName()));
    }

    @Test
    public void loadConferenceListByLeagueId() throws Exception {
        IConferenceFactory conferenceFactory = new ConferenceMock();
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
        IFreeAgentFactory loadFreeAgentFactory = new FreeAgentMock();
        league.loadFreeAgentByLeagueId(loadFreeAgentFactory);
        assertTrue(league.getFreeAgent().getLeagueId() == league.getId());
    }


    @Test
    public void getTradingOfferListTest() throws Exception {
        League league = new League(1, leagueFactory);
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        assertEquals(league.getTradeOfferList().get(0).getId(), tradeOffer.getId());
        assertEquals(league.getTradeOfferList().get(0).getFromTeamId(), tradeOffer.getFromTeamId());
    }

    @Test
    public void setTradingOfferListTest() throws Exception {
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();
        TradeOffer tradeOffer = new TradeOffer(1, tradeOfferFactory);
        TradeOffer tradeOffer1 = new TradeOffer(2, tradeOfferFactory);
        List<TradeOffer> tradeOfferList = new ArrayList<>();
        tradeOfferList.add(tradeOffer);
        tradeOfferList.add(tradeOffer1);
        League league = new League(1, leagueFactory);
        league.setTradeOfferList(tradeOfferList);
        assertEquals(league.getTradeOfferList().get(0).getId(), tradeOffer.getId());
        assertEquals(league.getTradeOfferList().get(1).getId(), tradeOffer1.getId());
        assertNotEquals(league.getTradeOfferList().get(1).getId(), tradeOffer.getId());
        assertNotEquals(league.getTradeOfferList().get(0).getId(), tradeOffer1.getId());
    }

    @Test
    public void loadTradingOfferDetailsByLeagueId() throws Exception {
        League league = new League();
        league.setId(1);
        ITradeOfferFactory tradeOfferFactory = new TradeOfferMock();
        league.loadTradingOfferDetailsByLeagueId(tradeOfferFactory);
        assertEquals(league.getTradeOfferList().get(0).getId(), 1);
        assertNotEquals(league.getTradeOfferList().get(1).getId(), 1);
    }

}
