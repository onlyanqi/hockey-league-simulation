package model;

import simulation.data.IAddPlayerFactory;
import simulation.data.ILoadPlayerFactory;
import org.junit.Test;
import org.junit.BeforeClass;
import simulation.model.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class PlayerTest {

    private static ILoadPlayerFactory loadPlayerFactory;
    private static IAddPlayerFactory addPlayerFactory;

    @BeforeClass
    public static void setFactoryObj(){
        loadPlayerFactory = new LoadPlayerMock();
        addPlayerFactory = new AddPlayerMock();
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

        player = new Player(2, loadPlayerFactory);
        assertNull(player.getName());
    }

    @Test
    public void getAgeTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertEquals(player.getAge(), 15);
    }

    @Test
    public void setAgeTest(){
        Player player = new Player();
        int age = 15;
        player.setAge(age);
        assertEquals(player.getAge(), age);
    }

    @Test
    public void getHomeTownTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertTrue(player.getHometown().equals("Halifax"));
    }

    @Test
    public void setHomeTownTest(){
        Player player = new Player();
        String homeTown = "Halifax";
        player.setHometown(homeTown);
        assertTrue(player.getHometown().equals(homeTown));
    }

    @Test
    public void getPositionTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertTrue(player.getPosition().equals("goalie"));
    }

    @Test
    public void setPositionTest(){
        Player player = new Player();
        String postion = "goalie";
        player.setPosition(postion);
        assertTrue(player.getPosition().equals(postion));
    }

    @Test
    public void getTeamIdTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertTrue(player.getTeamId() == (1));
    }

    @Test
    public void setTeamIdTest(){
        Player player = new Player();
        int teamId = 1;
        player.setTeamId(teamId);
        assertTrue(player.getTeamId() == teamId);
    }

    @Test
    public void isCaptainTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertTrue(player.isCaptain());
    }

    @Test
    public void setCaptainTest(){
        Player player = new Player();
        boolean isCaptain = true;
        player.setCaptain(true);
        assertTrue(player.isCaptain());
    }

    @Test
    public void validPositionTest(){
        Player player = new Player();
        player.setPosition("goalie");
        assertTrue(player.validPosition());
    }

    @Test
    public void addPlayerTest() throws Exception {
        Player player = new Player();
        player.setId(1);
        player.setName("Player1");
        player.addPlayer(addPlayerFactory);
        assertTrue(1 == player.getId());
        assertTrue("Player1".equals(player.getName()));
    }

    @Test
    public void getFreeAgentIdTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertTrue(player.getFreeAgentId() == (1));
    }

    @Test
    public void setFreeAgentIdTest(){
        Player player = new Player();
        int freeAgentId = 1;
        player.setFreeAgentId(freeAgentId);
        assertTrue(player.getFreeAgentId() == freeAgentId);
    }

    @Test
    public void getSeasonIdTest() throws Exception {
        Player player = new Player(1, loadPlayerFactory);
        assertTrue(player.getSeasonId() == (1));
    }

    @Test
    public void setSeasonIdTest(){
        Player player = new Player();
        int seasonId = 1;
        player.setSeasonId(seasonId);
        assertTrue(player.getSeasonId() == seasonId);
    }

}
