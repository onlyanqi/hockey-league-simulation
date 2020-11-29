package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.IGameDao;
import simulation.dao.IUserDao;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.GameMock;
import simulation.mock.UserMock;
import simulation.model.Game;
import simulation.model.User;

import static org.junit.Assert.*;

public class SimulateGameStateTest {
    private static IHockeyContext hockeyContext;
    private static IUserDao userFactory;
    private static IGameDao gameFactory;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        gameFactory = new GameMock();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void initConstructorTest() {
        SimulateGameState state = new SimulateGameState(hockeyContext);
        assertTrue(state instanceof SimulateGameState);
        assertTrue(state instanceof ISimulateState);
    }


    @Test
    public void simulateGame() throws Exception {
        Game g = new Game(4, gameFactory);
        assertFalse(g.getPlayed());
        assertNull(g.getWinner());
        SimulateGameState state = new SimulateGameState(hockeyContext);
        state.simulateGame(g);
        assertTrue(g.getPlayed());
    }


    @Test
    public void exitTest() {
        SimulateGameState state = new SimulateGameState(hockeyContext);
        assertTrue(state.exit() instanceof InjuryCheckState);
        assertTrue(state.exit() instanceof ISimulateState);
    }
}
