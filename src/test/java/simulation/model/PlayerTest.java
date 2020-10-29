package simulation.model;

import db.data.IPlayerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.model.mock.PlayerMock;

import static org.junit.Assert.*;

public class PlayerTest {

    private static IPlayerFactory loadPlayerFactory;

    @BeforeClass
    public static void setFactoryObj() {
        loadPlayerFactory = new PlayerMock();
    }

    @Test
    public void defaultConstructorTest() {
        Player player = new Player();
        assertEquals(player.getId(), 0);
    }

    @Test
    public void playerTest() {
        Player player = new Player(1);
        assertEquals(player.getId(), 1);
    }

    @Test
    public void playerFactoryTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertEquals(player.getId(), 1);
        assertEquals(player.getName(), "Player1");

        player = new Player(21, loadPlayerFactory);
        assertNull(player.getName());
    }

    @Test
    public void getAgeTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertEquals(player.getAge(), 27);
    }

    @Test
    public void setAgeTest() {
        Player player = new Player();
        int age = 15;
        player.setAge(age);
        assertEquals(player.getAge(), age);
    }

    @Test
    public void getPositionTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertEquals(player.getPosition(), Player.Position.valueOf("forward"));
    }

    @Test
    public void setPositionTest() {
        Player player = new Player();
        Player.Position position = Player.Position.valueOf("goalie");
        player.setPosition(position);
        assertEquals(player.getPosition(), position);
    }

    @Test
    public void getTeamIdTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertEquals(player.getTeamId(), (1));
    }

    @Test
    public void setTeamIdTest() {
        Player player = new Player();
        int teamId = 1;
        player.setTeamId(teamId);
        assertEquals(player.getTeamId(), teamId);
    }

    @Test
    public void isCaptainTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertTrue(player.isCaptain());
    }

    @Test
    public void setCaptainTest() {
        Player player = new Player();
        boolean isCaptain = true;
        player.setCaptain(true);
        assertTrue(player.isCaptain());
    }

    @Test
    public void addPlayerTest() throws Exception {
        Player player = new Player();
        player.setId(1);
        player.setName("Player1");
        player.addPlayer(loadPlayerFactory);
        assertEquals(1, player.getId());
        assertEquals("Player1", player.getName());
    }

    @Test
    public void getFreeAgentIdTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertEquals(player.getFreeAgentId(), (1));
    }

    @Test
    public void setFreeAgentIdTest() {
        Player player = new Player();
        int freeAgentId = 1;
        player.setFreeAgentId(freeAgentId);
        assertEquals(player.getFreeAgentId(), freeAgentId);
    }


}
