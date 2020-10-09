package factory;

import dao.AddLeagueDao;
import dao.LoadLeagueDao;
import data.IAddLeagueFactory;
import data.ILoadLeagueFactory;
import model.League;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class LeagueConcreteTest {

    private LeagueConcrete leagueConcrete;

    @Before
    public void init(){
        leagueConcrete = new LeagueConcrete();
    }

    @Test
    public void newLeagueTest(){
        assertTrue(leagueConcrete.newLeague() instanceof League);
    }

    @Test
    public void newLoadLeagueFactoryTest(){
        assertTrue(leagueConcrete.newLoadLeagueFactory() instanceof LoadLeagueDao);
    }

    @Test
    public void newLeagueNameUserIdTest() throws Exception {
        String leagueName = "Name";
        int userId = 1;
        ILoadLeagueFactory loadLeagueFactory = leagueConcrete.newLoadLeagueFactory();
        League league = leagueConcrete.newLeagueNameUserId(leagueName, userId, loadLeagueFactory);
        assertTrue(league instanceof League);
    }

    @Test
    public void newAddLeagueFactory(){
        assertTrue(leagueConcrete.newAddLeagueFactory() instanceof AddLeagueDao);
    }

}
