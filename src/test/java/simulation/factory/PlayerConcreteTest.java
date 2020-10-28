package simulation.factory;

import db.dao.PlayerDao;
import org.junit.Before;
import org.junit.Test;
import simulation.model.Player;

import static org.junit.Assert.assertTrue;

public class PlayerConcreteTest {

    private PlayerConcrete playerConcrete;

    @Before
    public void init() {
        playerConcrete = new PlayerConcrete();
    }

    @Test
    public void newPlayerTest() {
        assertTrue(playerConcrete.newPlayer() instanceof Player);
    }

    @Test
    public void newLoadPlayerFactoryTest() {
        assertTrue(playerConcrete.newLoadPlayerFactory() instanceof PlayerDao);
    }

    @Test
    public void newAddPlayerFactoryTest() {
        assertTrue(playerConcrete.newAddPlayerFactory() instanceof PlayerDao);
    }

}
