package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FreeAgentTest {

    private static IDaoFactory daoFactory;
    private static IFreeAgentDao freeAgentDao;
    private static IModelFactory modelFactory;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        freeAgentDao = daoFactory.newFreeAgentDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void defaultConstructorTest() {
        IFreeAgent freeAgent = modelFactory.newFreeAgent();
        assertNotEquals(freeAgent.getId(), 0);
    }

    @Test
    public void freeAgentTest() {
        IFreeAgent freeAgent = modelFactory.newFreeAgentWithId(1);
        assertEquals(freeAgent.getId(), 1);
    }

    @Test
    public void freeAgentFactoryTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.newFreeAgentWithIdDao(1, freeAgentDao);
        List<IPlayer> playerList = freeAgent.getPlayerList();
        IPlayer player = playerList.get(0);
        assertEquals(player.getId(), 1);
        assertTrue(player.getName().equals("Player1"));
    }

    @Test
    public void getSeasonIdTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.newFreeAgentWithIdDao(1, freeAgentDao);
        assertTrue(freeAgent.getSeasonId() == (1));
    }

    @Test
    public void setSeasonIdTest() {
        IFreeAgent freeAgent = modelFactory.newFreeAgent();
        int seasonId = 1;
        freeAgent.setSeasonId(seasonId);
        assertTrue(freeAgent.getSeasonId() == seasonId);
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.newFreeAgentWithIdDao(1, freeAgentDao);
        assertTrue(freeAgent.getLeagueId() == (1));
    }

    @Test
    public void setLeagueIdTest() {
        IFreeAgent freeAgent = modelFactory.newFreeAgent();
        int leagueId = 1;
        freeAgent.setLeagueId(leagueId);
        assertTrue(freeAgent.getLeagueId() == leagueId);
    }

    @Test
    public void getPlayerListTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.newFreeAgentWithIdDao(1, freeAgentDao);
        List<IPlayer> playerList = freeAgent.getPlayerList();
        assertNotNull(playerList);
        assertTrue(playerList.get(0).getId() == (1));
        assertTrue(playerList.get(4).getId() == (5));
        assertTrue(playerList.get(0).getName().equals("Player1"));
        assertTrue(playerList.get(4).getName().equals("Player5"));
    }

    @Test
    public void setPlayerListTest() throws Exception {
        IPlayerDao playerFactory = daoFactory.newPlayerDao();
        List<IPlayer> playerList = new ArrayList<>();
        IPlayer player = modelFactory.newPlayerWithIdDao(1, playerFactory);
        playerList.add(player);
        player = modelFactory.newPlayerWithIdDao(5, playerFactory);
        playerList.add(player);

        IFreeAgent freeAgent = modelFactory.newFreeAgent();
        freeAgent.setPlayerList(playerList);

        assertTrue(freeAgent.getPlayerList().get(0).getId() == (1));
        assertTrue(freeAgent.getPlayerList().get(1).getId() == (5));
        assertTrue(freeAgent.getPlayerList().get(0).getName().equals("Player1"));
        assertTrue(freeAgent.getPlayerList().get(1).getName().equals("Player5"));
    }

    @Test
    public void addFreeAgentTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.newFreeAgent();
        freeAgent.setId(1);
        freeAgent.setName("FreeAgent1");
        freeAgent.addFreeAgent(freeAgentDao);
        assertTrue(1 == freeAgent.getId());
        assertTrue("FreeAgent1".equals(freeAgent.getName()));
    }

    @Test
    public void loadPlayerListByFreeAgentIdTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.newFreeAgentWithId(1);
        IPlayerDao playerFactory = daoFactory.newPlayerDao();
        freeAgent.loadPlayerListByFreeAgentId(playerFactory);

        assertTrue(freeAgent.getPlayerList().get(0).getId() == 1);
        assertTrue(freeAgent.getPlayerList().get(19).getId() == 20);
        assertTrue(freeAgent.getPlayerList().get(0).getName().equals("Player1"));
        assertTrue(freeAgent.getPlayerList().get(19).getName().equals("Player20"));
    }

    @Test
    public void getGoodFreeAgentsListTest() throws Exception {
        IFreeAgent freeAgent = modelFactory.newFreeAgentWithIdDao(1, freeAgentDao);
        List<IPlayer> playerList = freeAgent.getPlayerList();
        ITeamDao teamFactory = daoFactory.newTeamDao();
        ITeam team = modelFactory.newTeamWithIdDao(1, teamFactory);
        List<Double> strengthList = team.createStrengthList(playerList);
        assertTrue(freeAgent.getGoodFreeAgentsList(strengthList).size() <= playerList.size());
        assertFalse(freeAgent.getGoodFreeAgentsList(strengthList).size() > playerList.size());
    }

    @Test
    public void calculateStrengthAverageTest() {
        List<Double> strengthList = new ArrayList<>();
        Double strength = 10.0;
        strengthList.add(strength);
        strengthList.add(strength);
        IFreeAgent freeAgent = modelFactory.newFreeAgent();
        Double avg = freeAgent.calculateStrengthAverage(strengthList);
        assertTrue(avg == 10.0);
        assertFalse(avg == 0);
    }


}
