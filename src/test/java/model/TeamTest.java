package model;

import data.IPlayerFactory;
import data.ITeamFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TeamTest {

    private static ITeamFactory factory;

    @BeforeAll
    static void setFactoryObj(){
        factory = new TeamMock();
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
    public void teamFactoryTest(){
        Team team = new Team(1, factory);
        assertEquals(team.getId(), 1);
        assertEquals(team.getName(), "Team1");

        team = new Team(2, factory);
        assertNull(team.getName());
    }

    @Test
    public void getHomeTownTest(){
        Team team = new Team(1, factory);
        assertTrue(team.getHometown().equals("Halifax"));
    }

    @Test
    public void setHomeTownTest(){
        Team team = new Team();
        String homeTown = "Halifax";
        team.setHometown(homeTown);
        assertTrue(team.getHometown().equals(homeTown));
    }

    @Test
    public void getMascotTest(){
        Team team = new Team(1, factory);
        assertTrue(team.getMascot().equals("Tiger"));
    }

    @Test
    public void setMascotTest(){
        Team team = new Team();
        String mascot = "Tiger";
        team.setMascot(mascot);
        assertTrue(team.getMascot().equals(mascot));
    }

    @Test
    public void getDivisionIdTest(){
        Team team = new Team(1, factory);
        assertTrue(team.getDivisionId() == (1));
    }

    @Test
    public void setDivisionIdTest(){
        Team team = new Team();
        long divisionId = 1;
        team.setDivisionId(divisionId);
        assertTrue(team.getDivisionId() == divisionId);
    }

    @Test
    public void getPlayerListTest(){
        Team team = new Team(1, factory);
        List<Player> playerList = team.getPlayerList();
        assertNotNull(playerList);
        assertTrue(playerList.get(0).getId() == (1));
        assertTrue(playerList.get(1).getId() == (5));
        assertTrue(playerList.get(0).getName().equals("Player1"));
        assertTrue(playerList.get(1).getName().equals("Player5"));
    }

    @Test
    public void setPlayerListTest(){
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


}
