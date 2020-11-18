package simulation.factory;

import db.data.IPlayerFactory;
import org.junit.Before;
import org.junit.Test;
import simulation.model.Player;
import simulation.model.SharedAttributes;

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
        assertTrue(playerConcrete.newPlayer() instanceof SharedAttributes);
    }


}
