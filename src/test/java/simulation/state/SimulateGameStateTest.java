package simulation.state;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.*;
import simulation.model.Game;
import simulation.model.User;

import static org.junit.Assert.*;

public class SimulateGameStateTest {
    private static HockeyContext hockeyContext;
    private static IUserFactory userFactory;
    private static IGameFactory gameFactory;

    @BeforeClass
    public static void init() throws Exception {
        userFactory = new UserMock();
        hockeyContext = new HockeyContext();
        gameFactory = new GameMock();
        User user = new User(4, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void initConstructorTest(){
        SimulateGameState state = new SimulateGameState(hockeyContext);
        assertTrue(state instanceof SimulateGameState);
        assertTrue(state instanceof ISimulateState);
    }


    @Test
    public void simulateGame() throws Exception {
        Game g = new Game(4,gameFactory);
        assertFalse(g.getPlayed());
        assertNull(g.getWinner());
        SimulateGameState state = new SimulateGameState(hockeyContext);
        state.simulateGame(g);
    }


    @Test
    public void exitTest(){
        SimulateGameState state = new SimulateGameState(hockeyContext);
        assertTrue(state.exit() instanceof InjuryCheckState);
        assertTrue(state.exit() instanceof ISimulateState);
    }
}
