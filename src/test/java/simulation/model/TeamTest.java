package simulation.model;

import db.data.IPlayerFactory;
import db.data.ITeamFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.PlayerMock;
import simulation.mock.TeamMock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TeamTest {

    private static ITeamFactory loadTeamFactory;

    @BeforeClass
    public static void setFactoryObj() {
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
    public void getMascotTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
        assertEquals(team.getMascot(), ("Tiger1"));
    }

    @Test
    public void setMascotTest() {
        Team team = new Team();
        String mascot = "Tiger";
        team.setMascot(mascot);
        assertEquals(team.getMascot(), (mascot));
    }

    @Test
    public void getGeneralManagerTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
        assertEquals(team.getManager().getName(), ("Manager1"));
    }

    @Test
    public void setGeneralManagerTest() {
        Team team = new Team();
        Manager manager = new Manager();
        manager.setName("generalManager");
        team.setManager(manager);
        assertEquals(team.getManager().getName(), ("generalManager"));
    }

    @Test
    public void getHeadCoachTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
        assertEquals(team.getCoach().getName(), ("Coach1"));
    }

    @Test
    public void setHeadCoachTest() {
        Team team = new Team();
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
        assertEquals(team.getDivisionId(), (1));
    }

    @Test
    public void setDivisionIdTest() {
        Team team = new Team();
        int divisionId = 1;
        team.setDivisionId(divisionId);
        assertEquals(team.getDivisionId(), divisionId);
    }

    @Test
    public void getPlayerListTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
        List<Player> playerList = team.getPlayerList();
        assertNotNull(playerList);
        assertEquals(1, playerList.get(0).getId());
        assertEquals(5, playerList.get(4).getId());
        assertEquals("Player1", playerList.get(0).getName());
        assertEquals("Player5", playerList.get(4).getName());
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
        assertEquals(1, team.getId());
        assertEquals("Team1", (team.getName()));
    }

    @Test
    public void loadPlayerListByTeamIdTest() throws Exception {
        Team team = new Team(1);
        IPlayerFactory playerFactory = new PlayerMock();
        team.loadPlayerListByTeamId(playerFactory);

        assertEquals(team.getPlayerList().get(0).getId(), (1));
        assertEquals(team.getPlayerList().get(1).getId(), (5));
        assertEquals(team.getPlayerList().get(0).getName(), ("Player1"));
        assertEquals(team.getPlayerList().get(1).getName(), ("Player5"));
    }

    @Test
    public void getPendingTradeOfferCount() throws Exception {
        Team team = new Team(1, loadTeamFactory);
        assertEquals(team.getTradeOfferCountOfSeason(), 0);
        assertNotEquals(team.getTradeOfferCountOfSeason(), 2);
        team = new Team(2, loadTeamFactory);
        assertEquals(team.getTradeOfferCountOfSeason(), 2);
        assertNotEquals(team.getTradeOfferCountOfSeason(), 1);
    }

    @Test
    public void setPendingTradeOfferCountTest() {
        Team team = new Team();
        int pendingTradeOfferCount = 1;
        team.setTradeOfferCountOfSeason(pendingTradeOfferCount);
        assertEquals(team.getTradeOfferCountOfSeason(), pendingTradeOfferCount);
        assertNotEquals(team.getTradeOfferCountOfSeason(), 2);
    }

    @Test
    public void getLossPointTest() throws Exception {
        Team team = new Team(1, loadTeamFactory);
        assertEquals(team.getLossPoint(), 0);
        assertNotEquals(team.getTradeOfferCountOfSeason(), 2);
        team = new Team(2, loadTeamFactory);
        assertEquals(team.getLossPoint(), 2);
        assertNotEquals(team.getLossPoint(), 1);
    }

    @Test
    public void setLossPointTest() {
        Team team = new Team();
        int lossPoint = 1;
        team.setLossPoint(lossPoint);
        assertEquals(team.getLossPoint(), lossPoint);
        assertNotEquals(team.getLossPoint(), 2);
    }


}
