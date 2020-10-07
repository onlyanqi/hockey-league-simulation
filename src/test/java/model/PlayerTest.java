package model;

import data.IPlayerFactory;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class PlayerTest {

    private static IPlayerFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new PlayerMock();
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
        Player player = new Player(1, factory);
        assertEquals(player.getId(), 1);
        assertEquals(player.getName(), "Player1");

        player = new Player(2, factory);
        assertNull(player.getName());
    }

    @Test
    public void getAgeTest() throws Exception {
        Player player = new Player(1, factory);
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
        Player player = new Player(1, factory);
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
        Player player = new Player(1, factory);
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
        Player player = new Player(1, factory);
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
        Player player = new Player(1, factory);
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


}
