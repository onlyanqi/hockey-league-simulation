package factory;

import dao.AddPlayerDao;
import dao.LoadPlayerDao;
import model.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PlayerConcreteTest {

    private PlayerConcrete playerConcrete;

    @Before
    public void init(){
        playerConcrete = new PlayerConcrete();
    }

    @Test
    public void newPlayerTest(){
        assertTrue(playerConcrete.newPlayer() instanceof Player);
    }

    @Test
    public void newLoadPlayerFactoryTest(){
        assertTrue(playerConcrete.newLoadPlayerFactory() instanceof LoadPlayerDao);
    }

    @Test
    public void newAddPlayerFactoryTest(){
        assertTrue(playerConcrete.newAddPlayerFactory() instanceof AddPlayerDao);
    }

}
