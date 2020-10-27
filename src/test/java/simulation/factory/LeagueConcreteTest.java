package simulation.factory;


import db.dao.LeagueDao;
import db.data.ILeagueFactory;
import simulation.model.League;
import org.junit.Before;
import org.junit.Test;
import simulation.model.LeagueMock;

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
        assertTrue(leagueConcrete.newLoadLeagueFactory() instanceof LeagueDao);
    }

    @Test
    public void newLeagueNameUserIdTest() throws Exception {
        String leagueName = "Name";
        int userId = 1;
        ILeagueFactory loadLeagueFactory = new LeagueMock();
        League league = leagueConcrete.createLeagueFromNameAndUserId(leagueName, userId, loadLeagueFactory);
        assertTrue(league instanceof League);
    }

    @Test
    public void newAddLeagueFactory(){
        assertTrue(leagueConcrete.newAddLeagueFactory() instanceof LeagueDao);
    }

}
