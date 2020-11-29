package simulation.state;


import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.IGameDao;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.GameMock;
import simulation.mock.UserMock;
import simulation.model.GameSimulation;
import simulation.model.IGameSimulation;
import simulation.model.ITeam;
import simulation.model.User;
import simulation.state.gamestatemachine.GameContext;

import static org.junit.Assert.*;

public class GameContextTest{
    private static IHockeyContext hockeyContext;
    private static IUserDao userFactory;
    private static IGameDao gameFactory;
    private static IHockeyContextFactory hockeyContextFactory;
    private static ITeam team11;
    private static ITeam team12;
    private static IGameSimulation gameSimulation;

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
        gameSimulation = hockeyContext.getModelFactory().newGameSimulationFromTeams(team11,team12);
        gameSimulation.setTeam1Shift(gameSimulation.getTeam1Shift().getShift(team11,gameSimulation.getTeamPlayersCount()));
        gameSimulation.setTeam2Shift(gameSimulation.getTeam2Shift().getShift(team12,gameSimulation.getTeamPlayersCount()));
    }

    @Test
    public void testConstructor(){
        GameContext gameContext = new GameContext(gameSimulation);
        assertTrue(gameContext instanceof GameContext);
    }

    @Test
    public void testStart() throws Exception {
        GameContext gameContext = new GameContext(gameSimulation);
        gameContext.start();
        assertNull(gameContext.getGameState());
    }
    @Test
    public void testGetOffensive() throws Exception {
        GameContext gameContext = new GameContext(gameSimulation);
        gameContext.start();
        assertNotNull(gameContext.getOffensive());
    }
    @Test
    public void testSetOffensive() throws Exception {
        GameContext gameContext = new GameContext(gameSimulation);
        gameContext.setOffensive(gameSimulation.getTeam2Shift());
        assertEquals(gameContext.getOffensive(),gameSimulation.getTeam2Shift());
    }
    @Test
    public void testGetDefensive() throws Exception {
        GameContext gameContext = new GameContext(gameSimulation);
        gameContext.start();
        assertNotNull(gameContext.getDefensive());
    }
    @Test
    public void testSetDefensive() {
        GameContext gameContext = new GameContext(gameSimulation);
        gameContext.setDefensive(gameSimulation.getTeam2Shift());
        assertEquals(gameContext.getDefensive(),gameSimulation.getTeam2Shift());
    }
    @Test
    public void testGetGameSimulation() throws Exception {
        GameContext gameContext = new GameContext(gameSimulation);
        gameContext.start();
        assertNotNull(gameContext.getGameSimulation());
    }
    @Test
    public void testSetGameSimulation() throws Exception {
        GameContext gameContext = new GameContext(gameSimulation);
        gameContext.setGameSimulation(gameSimulation);
        assertEquals(gameContext.getGameSimulation(),gameSimulation);
        assertNotNull(gameContext.getGameSimulation());
    }
}