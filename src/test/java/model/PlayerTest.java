package model;

import data.IParentObjFactory;
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
    public void playerFactoryTest(){
        Player player = new Player(1, factory);
        assertEquals(player.getId(), 1);
        assertEquals(player.getName(), "Player1");

        player = new Player(2, factory);
        assertNull(player.getName());
    }

    @Test
    public void getAgeTest(){
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
    public void getHomeTownTest(){
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
    public void getRoleTest(){
        Player player = new Player(1, factory);
        assertTrue(player.getRole().equals("goalie"));
    }

    @Test
    public void setRoleTest(){
        Player player = new Player();
        String role = "goalie";
        player.setRole(role);
        assertTrue(player.getRole().equals(role));
    }

    @Test
    public void getTeamIdTest(){
        Player player = new Player(1, factory);
        assertTrue(player.getTeamId() == (1));
    }

    @Test
    public void setTeamIdTest(){
        Player player = new Player();
        long teamId = 1;
        player.setTeamId(teamId);
        assertTrue(player.getTeamId() == teamId);
    }

    @Test
    public void validRoleTest(){
        Player player = new Player();
        player.setRole("goalie");
        assertTrue(player.validRole());
    }


}
