package simulation.model;

import db.data.IPlayerFactory;
import db.data.ITeamFactory;
import org.junit.Test;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TeamTest {

    private static ITeamFactory loadTeamFactory;

    @BeforeClass
    public static void setFactoryObj(){
        loadTeamFactory = new TeamMock();
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
        Team team = new Team(1, loadTeamFactory);
        assertEquals(team.getId(), 1);
        assertEquals(team.getName(), "Team1");

        team = new Team(2, loadTeamFactory);
        assertNull(team.getName());
    }

    @Test
    public void getHomeTownTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
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
        Team team = new Team(1, loadTeamFactory);
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
        Team team = new Team(1, loadTeamFactory);
        assertTrue(team.getManager().getName().equals("Manager1"));
    }

    @Test
    public void setGeneralManagerTest(){
        Team team = new Team();
        Manager manager = new Manager();
        manager.setName("generalManager");
        team.setManager(manager);
        assertTrue(team.getManager().getName().equals("generalManager"));
    }

    @Test
    public void getHeadCoachTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
        assertTrue(team.getCoach().getName().equals("Coach1"));
    }

    @Test
    public void setHeadCoachTest(){
        Team team = new Team();
        String headCoach = "Tiger";
        Coach Coach1 = new Coach();
        Coach1.setName("Rob");
        Coach1.setSkating(0.8);
        Coach1.setShooting(0.5);
        Coach1.setChecking(0.3);
        Coach1.setSaving(0.5);
        team.setCoach(Coach1);
        assertTrue(team.getCoach().equals(Coach1));
    }

    @Test
    public void getDivisionIdTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
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
        Team team = new Team(1, loadTeamFactory);
        List<Player> playerList = team.getPlayerList();
        assertNotNull(playerList);
        assertTrue(playerList.get(0).getId() == (1));
        assertTrue(playerList.get(1).getId() == (5));
        assertTrue(playerList.get(0).getName().equals("Player1"));
        assertTrue(playerList.get(1).getName().equals("Player5"));
    }

    @Test
    public void setPlayerListTest() throws Exception {
        IPlayerFactory playerFactory = new PlayerMock();
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

    @Test
    public void addTeamTest() throws Exception {
        Team team = new Team();
        team.setId(1);
        team.setName("Team1");
        team.addTeam(loadTeamFactory);
        assertTrue(1 == team.getId());
        assertTrue("Team1".equals(team.getName()));
    }

    @Test
    public void loadPlayerListByTeamIdTest() throws Exception {
        Team team = new Team(1);
        IPlayerFactory playerFactory = new PlayerMock();
        team.loadPlayerListByTeamId(playerFactory);

        assertTrue(team.getPlayerList().get(0).getId() == (1));
        assertTrue(team.getPlayerList().get(1).getId() == (5));
        assertTrue(team.getPlayerList().get(0).getName().equals("Player1"));
        assertTrue(team.getPlayerList().get(1).getName().equals("Player5"));
    }

}
