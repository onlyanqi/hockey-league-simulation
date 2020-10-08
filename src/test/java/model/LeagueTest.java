package model;

import data.ILoadConferenceFactory;
import data.ILoadLeagueFactory;
import data.ILoadPlayerFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LeagueTest {

    private static ILoadLeagueFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new LoadLeagueMock();
    }

    @Test
    public void defaultConstructorTest() {
        League league = new League();
        assertEquals(league.getId(), 0);
    }

    @Test
    public void leagueTest() {
        League league = new League(1);
        assertEquals(league.getId(), 1);
    }

    @Test
    public void leagueFactoryTest() throws Exception{
        League league = new League(1, factory);
        assertEquals(league.getId(), 1);
        assertEquals(league.getName(), "League1");

        league = new League(2, factory);
        assertNull(league.getName());
    }

    @Test
    public void getCountryTest() throws Exception{
        League league = new League(1, factory);
        assertTrue(league.getCountry().equals("Canada"));
    }

    @Test
    public void setCountryTest(){
        League league = new League();
        String country = "Canada";
        league.setCountry(country);
        assertTrue(league.getCountry().equals(country));
    }

    @Test
    public void getCreatedByTest() throws Exception{
        League league = new League(1, factory);
        assertTrue(league.getCreatedBy() == 1);
    }

    @Test
    public void setCreatedByTest(){
        League league = new League();
        int createdBy = 1;
        league.setCreatedBy(createdBy);
        assertTrue(league.getCreatedBy() == (createdBy));
    }

    @Test
    public void getConferenceListTest() throws Exception{
        League league = new League(1, factory);
        List<Conference> conferenceList = league.getConferenceList();
        assertNotNull(conferenceList);

        assertTrue(conferenceList.get(0).getId() == (1));
        assertTrue(conferenceList.get(1).getId() == (2));
        assertTrue(conferenceList.get(0).getName().equals("Conference1"));
        assertNull(conferenceList.get(1).getName());
    }

    @Test
    public void setConferenceListTest() throws Exception {
        ILoadConferenceFactory conferenceFactory = new LoadConferenceMock();
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
    public void getFreeAgentTest() throws Exception {
        League league = new League(1, factory);
        assertEquals(league.getFreeAgent().getId(), 1);
        List<Player> playerList = league.getFreeAgent().getPlayerList();
        assertTrue(playerList.get(0).getName().equals("Player1"));
    }

    @Test
    public void setFreeAgentTest() throws Exception{
        FreeAgent freeAgent = new FreeAgent();
        League league = new League();
        ILoadPlayerFactory playerFactory = new LoadPlayerMock();
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

}
