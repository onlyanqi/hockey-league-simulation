package simulation.factory;


import db.data.ILeagueDao;
import org.junit.Before;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.model.ILeague;
import simulation.model.League;

import static org.junit.Assert.assertTrue;

public class LeagueConcreteTest {

    private LeagueConcrete leagueConcrete;

    @Before
    public void init() {
        leagueConcrete = new LeagueConcrete();
    }

    @Test
    public void newLeagueTest() {
        assertTrue(leagueConcrete.newLeague() instanceof League);
    }


    @Test
    public void newLeagueNameUserIdTest() throws Exception {
        String leagueName = "Name";
        int userId = 1;
        ILeagueDao loadLeagueFactory = new LeagueMock();
        ILeague league = leagueConcrete.createLeagueFromNameAndUserId(leagueName, userId, loadLeagueFactory);
        assertTrue(league instanceof League);
    }

}
