package model;

import data.IFreeAgentFactory;
import data.IPlayerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FreeAgentTest {

    private static IFreeAgentFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new FreeAgentMock();
    }

    @Test
    public void defaultConstructorTest() {
        FreeAgent freeAgent = new FreeAgent();
        assertEquals(freeAgent.getId(), 0);
    }

    @Test
    public void freeAgentTest() {
        FreeAgent freeAgent = new FreeAgent(1);
        assertEquals(freeAgent.getId(), 1);
    }

    @Test
    public void freeAgentFactoryTest(){
        FreeAgent freeAgent = new FreeAgent(1, factory);
        freeAgent = new FreeAgent(1, factory);
        List<Player> playerList = freeAgent.getPlayerList();
        Player player = playerList.get(0);
        assertEquals(player.getId(),1);
        assertTrue(player.getName().equals("Player1"));
    }

    @Test
    public void getPlayerListTest(){
        FreeAgent freeAgent = new FreeAgent(1, factory);
        List<Player> playerList = freeAgent.getPlayerList();
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

        FreeAgent freeAgent = new FreeAgent();
        freeAgent.setPlayerList(playerList);

        assertTrue(freeAgent.getPlayerList().get(0).getId() == (1));
        assertTrue(freeAgent.getPlayerList().get(1).getId() == (5));
        assertTrue(freeAgent.getPlayerList().get(0).getName().equals("Player1"));
        assertTrue(freeAgent.getPlayerList().get(1).getName().equals("Player5"));
    }




}
