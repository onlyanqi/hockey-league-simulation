package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.IDaoFactory;
import persistance.dao.IPlayerDao;
import persistance.dao.ITeamDao;
import simulation.dao.DaoFactoryMock;
import simulation.mock.PlayerMock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TeamTest {

    private static ITeamDao teamDao;
    private static IModelFactory modelFactory;
    private static IDaoFactory daoFactory;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        teamDao = daoFactory.newTeamDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void defaultConstructorTest() {
        Team team = new Team();
        assertNotEquals(team.getId(), 0);
    }

    @Test
    public void teamTest() {
        Team team = new Team(1);
        assertEquals(team.getId(), 1);
    }

    @Test
    public void teamFactoryTest() throws Exception {
        Team team = new Team(1, teamDao);
        assertEquals(team.getId(), 1);
        assertEquals(team.getName(), "Team1");

        team = new Team(2, teamDao);
        assertNull(team.getName());
    }

    @Test
    public void getMascotTest() throws Exception {
        Team team = new Team(1, teamDao);
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
        Team team = new Team(1, teamDao);
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
        Team team = new Team(1, teamDao);
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
        Team team = new Team(1, teamDao);
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
        ITeam team = new Team(2, teamDao);
        List<IPlayer> playerList = team.getPlayerList();
        assertNotNull(playerList);
        assertEquals(1, playerList.get(0).getId());
        assertEquals(5, playerList.get(4).getId());
        assertEquals("Player1", playerList.get(0).getName());
        assertEquals("Player5", playerList.get(4).getName());
    }

    @Test
    public void setPlayerListTest() throws Exception {
        IPlayerDao playerFactory = new PlayerMock();
        List<IPlayer> playerList = new ArrayList<>();
        IPlayer player;
        for (int i = 1; i < 31; i++) {
            player = new Player(i, playerFactory);
            playerList.add(player);
        }

        Team team = new Team();
        team.setPlayerList(playerList);

        assertEquals(team.getPlayerList().get(0).getId(), (1));
        assertEquals(team.getPlayerList().get(1).getId(), (2));
        assertEquals(team.getPlayerList().get(0).getName(), ("Player1"));
        assertTrue(team.getPlayerList().get(1).getName().equals("Player2"));
    }

    @Test
    public void getActivePlayerListTest() throws Exception {
        Team team = new Team(1, teamDao);
        assertNotEquals(team.getActivePlayerList().get(1).getId(), (1));
        assertEquals(team.getActivePlayerList().get(1).getId(), (3));
    }

    @Test
    public void addTeamTest() throws Exception {
        Team team = new Team();
        team.setId(1);
        team.setName("Team1");
        team.addTeam(teamDao);
        assertEquals(1, team.getId());
        assertEquals("Team1", (team.getName()));
    }

    @Test
    public void loadPlayerListByTeamIdTest() throws Exception {
        Team team = new Team(1);
        IPlayerDao playerFactory = new PlayerMock();
        team.loadPlayerListByTeamId(playerFactory);

        assertEquals(team.getPlayerList().get(0).getId(), (1));
        assertEquals(team.getPlayerList().get(1).getId(), (2));
        assertEquals(team.getPlayerList().get(0).getName(), ("Player1"));
        assertEquals(team.getPlayerList().get(1).getName(), ("Player2"));
    }

    @Test
    public void checkNumPlayerTest() throws Exception {
        Team team = new Team(1, teamDao);
        assertTrue(team.checkNumPlayer(team.getPlayerList()));
        team.getPlayerList().remove(0);
        assertFalse(team.checkNumPlayer(team.getPlayerList()));
    }

    @Test
    public void getPlayersTradedCountTest() throws Exception {
        Team team = new Team(1, teamDao);
        assertEquals(team.getPlayersTradedCount(), 0);
        assertNotEquals(team.getPlayersTradedCount(), 2);
        team = new Team(2, teamDao);
        assertEquals(team.getPlayersTradedCount(), 2);
        assertNotEquals(team.getPlayersTradedCount(), 1);
    }

    @Test
    public void setPlayersTradedCountTest() {
        Team team = new Team();
        int tradeOfferCountOfSeason = 1;
        team.setPlayersTradedCount(tradeOfferCountOfSeason);
        assertEquals(team.getPlayersTradedCount(), tradeOfferCountOfSeason);
        assertNotEquals(team.getPlayersTradedCount(), 2);
    }

    @Test
    public void getLossPointTest() throws Exception {
        Team team = new Team(1, teamDao);
        assertEquals(team.getLossPoint(), 0);
        assertNotEquals(team.getPlayersTradedCount(), 2);
        team = new Team(2, teamDao);
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

    @Test
    public void fixTeamPlayerNumTest() throws Exception {
        ITeam team = modelFactory.createTeamWithIdDao(1, teamDao);
        List<IPlayer> playerList = team.getPlayerList();
        playerList.remove(0);
        IFreeAgent freeAgent = modelFactory.createFreeAgentWithIdDao(1, daoFactory.newFreeAgentDao());
        team.fixTeamPlayerNum(freeAgent.getPlayerList());
        int teamSize = team.getPlayerList().size();
        assertEquals(teamSize, 30);
        assertNotEquals(teamSize, 29);
    }

    @Test
    public void getDraftPicksTest() throws Exception {
        ITeam team = modelFactory.createTeamWithIdDao(1, teamDao);
        List<String> draftPicks = team.getDraftPicks();
        assertNotNull(draftPicks);
        int draftPickSize = draftPicks.size();
        assertEquals(draftPickSize, 7);
    }

    @Test
    public void setDraftPicksTest() throws Exception {
        ITeam team = modelFactory.createTeam();
        List<String> draftPicks = new ArrayList<>(Arrays.asList(
                null, null, null, null, null, null, null
        ));
        team.setDraftPicks(draftPicks);
        assertNotNull(team.getDraftPicks());
        int draftPickSize = team.getDraftPicks().size();
        assertEquals(draftPickSize, 7);
    }

}
