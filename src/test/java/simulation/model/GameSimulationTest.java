package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.IGameDao;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.GameMock;
import simulation.mock.UserMock;
import simulation.state.IHockeyContext;


import static org.junit.Assert.*;

public class GameSimulationTest{

    private static IHockeyContext hockeyContext;
    private static IUserDao userFactory;
    private static IGameDao gameFactory;
    private static IHockeyContextFactory hockeyContextFactory;
    private static ITeam team11;
    private static ITeam team12;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        gameFactory = new GameMock();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
        team11 = hockeyContext.getUser().getLeague().getTeamByTeamName("Team11");
        team12 = hockeyContext.getUser().getLeague().getTeamByTeamName("Team12");
    }

    @Test
    public void parameterConstructorTest(){
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertTrue(gameSimulation instanceof GameSimulation);
        assertFalse(gameSimulation instanceof IGame);
    }

    @Test
    public void testInitializeGameSimulation() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.initializeGameSimulation();
        assertTrue(gameSimulation.getTeamPlayersCount().size() ==2);
        assertTrue(gameSimulation.getShots().size() ==2);
        assertTrue(gameSimulation.getSaves().size() ==2);
        assertTrue(gameSimulation.getGoals().size() ==2);
        assertTrue(gameSimulation.getPenalties().size() ==2);
        assertTrue(gameSimulation.getPenalties().containsKey("Team11"));
        assertFalse(gameSimulation.getPenalties().size() ==0);

    }

    @Test
    public void testPlay() throws Exception {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.play();
        assertNotNull(gameSimulation.getTeam1Shift());
        assertNotNull(gameSimulation.getTeam2Shift());
        assertTrue(gameSimulation.getShots().get("Team11")>0);
        assertTrue(gameSimulation.getShots().get("Team12")>0);
    }

    @Test
    public void testGetTeam1Shift() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertNotNull(gameSimulation.getTeam1Shift());
    }

    @Test
    public void testSetTeam1Shift() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setTeam1Shift(new Shift());
        assertNotNull(gameSimulation.getTeam1Shift());
    }

    @Test
    public void testGetTeam2Shift() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertNotNull(gameSimulation.getTeam1Shift());
    }

    @Test
    public void testSetTeam2Shift() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setTeam2Shift(new Shift());
        assertNotNull(gameSimulation.getTeam2Shift());
    }

    @Test
    public void testGetTeam1() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertTrue(gameSimulation.getTeam1() instanceof ITeam);
        assertNotNull(gameSimulation.getTeam1());
    }

    @Test
    public void testSetTeam1() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setTeam1(team11);
        assertTrue(gameSimulation.getTeam1().getName().equals("Team11"));
    }

    @Test
    public void testGetTeam2() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertTrue(gameSimulation.getTeam2() instanceof ITeam);
        assertNotNull(gameSimulation.getTeam2());
    }

    @Test
    public void testSetTeam2() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setTeam2(team12);
        assertTrue(gameSimulation.getTeam2().getName().equals("Team12"));
    }

    @Test
    public void testGetTeamPlayersCount() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertNotNull(gameSimulation.getTeamPlayersCount().get("Team11"));
    }

    @Test
    public void testGetGoals() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertTrue(gameSimulation.getGoals().get("Team11") >=0);
    }

    @Test
    public void testSetGoals() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setGoals(gameSimulation.getGoals());
        assertTrue(gameSimulation.getGoals().get("Team11") >=0);
    }

    @Test
    public void testGetPenalties() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertTrue(gameSimulation.getPenalties().get("Team11") >=0);
    }

    @Test
    public void testSetPenalties() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setPenalties(gameSimulation.getPenalties());
        assertTrue(gameSimulation.getPenalties().get("Team11") >=0);
    }

    @Test
    public void testGetShots() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertTrue(gameSimulation.getShots().get("Team11") >=0);
    }

    @Test
    public void testSetShots() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setShots(gameSimulation.getShots());
        assertTrue(gameSimulation.getShots().get("Team11") >=0);
    }

    @Test
    public void testGetSaves() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        assertTrue(gameSimulation.getSaves().get("Team11") >=0);
    }

    @Test
    public void testSetSaves() {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.setSaves(gameSimulation.getSaves());
        assertTrue(gameSimulation.getSaves().get("Team11") >=0);
    }

    @Test
    public void testAddToPenaltyBox() throws Exception {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.play();
        gameSimulation.addToPenaltyBox(gameSimulation.getTeam1Shift(),gameSimulation.getTeam1Shift().getDefense().get(0));
        assertTrue(gameSimulation.getTeam1Shift().getPenalizedDefensePlayer().size()>0);
    }

    @Test
    public void testRemoveFromPenaltyBoxAndAddToShift() throws Exception {
        GameSimulation gameSimulation = new GameSimulation(team11,team12);
        gameSimulation.play();
        IPlayer player = gameSimulation.getTeam1Shift().getDefense().get(0);
        gameSimulation.removeFromPenaltyBoxAndAddToShift(gameSimulation.getTeam1Shift(),player);
        assertNull(gameSimulation.getTeam1Shift().getPenalizedDefensePlayer().get(player));
    }
}