package model;

import data.ILoadPlayerFactory;
import data.ILoadTeamFactory;
import org.junit.Test;
import org.junit.BeforeClass;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TeamTest {

    private static ILoadTeamFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new LoadTeamMock();
    }

    @Test
    public void defaultConstructorTest() {
        Team team = new Team();
        assertEquals(team.getId(), 0);
    }

    @Test
    public void teamTest() {
        Team team = new Team(1);
        assertEquals(team.getId(), 1);
    }

    @Test
    public void teamFactoryTest() throws Exception {
        Team team = new Team(1, factory);
        assertEquals(team.getId(), 1);
        assertEquals(team.getName(), "Team1");

        team = new Team(2, factory);
        assertNull(team.getName());
    }

    @Test
    public void getHomeTownTest() throws Exception {
        Team team = new Team(1, factory);
        assertTrue(team.getHometown().equals("Halifax1"));
    }

    @Test
    public void setHomeTownTest(){
        Team team = new Team();
        String homeTown = "Halifax";
        team.setHometown(homeTown);
        assertTrue(team.getHometown().equals(homeTown));
    }

    @Test
    public void getMascotTest() throws Exception {
        Team team = new Team(1, factory);
        assertTrue(team.getMascot().equals("Tiger1"));
    }

    @Test
    public void setMascotTest(){
        Team team = new Team();
        String mascot = "Tiger";
        team.setMascot(mascot);
        assertTrue(team.getMascot().equals(mascot));
    }

    @Test
    public void getGeneralManagerTest() throws Exception {
        Team team = new Team(1, factory);
        assertTrue(team.getGeneralManager().equals("Manager1"));
    }

    @Test
    public void setGeneralManagerTest(){
        Team team = new Team();
        String generalManager = "Tiger";
        team.setGeneralManager(generalManager);
        assertTrue(team.getGeneralManager().equals(generalManager));
    }

    @Test
    public void getHeadCoachTest() throws Exception {
        Team team = new Team(1, factory);
        assertTrue(team.getHeadCoach().equals("Coach1"));
    }

    @Test
    public void setHeadCoachTest(){
        Team team = new Team();
        String headCoach = "Tiger";
        team.setHeadCoach(headCoach);
        assertTrue(team.getHeadCoach().equals(headCoach));
    }

    @Test
    public void getDivisionIdTest() throws Exception {
        Team team = new Team(1, factory);
        assertTrue(team.getDivisionId() == (1));
    }

    @Test
    public void setDivisionIdTest(){
        Team team = new Team();
        int divisionId = 1;
        team.setDivisionId(divisionId);
        assertTrue(team.getDivisionId() == divisionId);
    }

    @Test
    public void getPlayerListTest() throws Exception {
        Team team = new Team(1, factory);
        List<Player> playerList = team.getPlayerList();
        assertNotNull(playerList);
        assertTrue(playerList.get(0).getId() == (1));
        assertTrue(playerList.get(1).getId() == (5));
        assertTrue(playerList.get(0).getName().equals("Player1"));
        assertTrue(playerList.get(1).getName().equals("Player5"));
    }

    @Test
    public void setPlayerListTest() throws Exception {
        ILoadPlayerFactory playerFactory = new LoadPlayerMock();
        List<Player> playerList = new ArrayList<>();
        Player player = new Player(1, playerFactory);
        playerList.add(player);
        player = new Player(5, playerFactory);
        playerList.add(player);

        Team team = new Team();
        team.setPlayerList(playerList);

        assertTrue(team.getPlayerList().get(0).getId() == (1));
        assertTrue(team.getPlayerList().get(1).getId() == (5));
        assertTrue(team.getPlayerList().get(0).getName().equals("Player1"));
        assertTrue(team.getPlayerList().get(1).getName().equals("Player5"));
    }




}
