package simulation.model;

import org.junit.Before;
import org.junit.Test;
import simulation.dao.ILeagueDao;
import simulation.dao.ITeamDao;
import simulation.dao.IUserDao;
import simulation.mock.LeagueMock;
import simulation.mock.TeamMock;
import simulation.mock.UserMock;

import static org.junit.Assert.assertTrue;

public class ModelFactoryTest {

    private IModelFactory modelFactory;

    @Before
    public void init() {
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void newDivisionTest() {
        assertTrue(modelFactory.newDivision() instanceof Division);
    }

    @Test
    public void newConferenceTest() {
        assertTrue(modelFactory.newConference() instanceof Conference);
    }

    @Test
    public void newFreeAgentTest() {
        assertTrue(modelFactory.newFreeAgent() instanceof FreeAgent);
    }

    @Test
    public void newLeagueTest() {
        assertTrue(modelFactory.newLeague() instanceof League);
    }


    @Test
    public void newLeagueNameUserIdTest() throws Exception {
        String leagueName = "Name";
        int userId = 1;
        ILeagueDao loadLeagueFactory = new LeagueMock();
        ILeague league = modelFactory.createLeagueFromNameAndUserId(leagueName, userId, loadLeagueFactory);
        assertTrue(league instanceof League);
    }

    @Test
    public void newSeasonTest() {
        assertTrue(modelFactory.newSeason() instanceof Season);
    }

    @Test
    public void newTeamTest() {
        assertTrue(modelFactory.newTeam() instanceof Team);
    }

    @Test
    public void newTeamByNameTest() throws Exception {
        String name = "Name";
        ITeamDao loadTeamFactory = new TeamMock();
        ITeam team = modelFactory.newTeamByName(name, loadTeamFactory);
        assertTrue(team instanceof Team);
    }

    @Test
    public void newTradingTest() {
        assertTrue(modelFactory.newTrading() instanceof Trading);
        assertTrue(modelFactory.newTrading() instanceof SharedAttributes);
    }

    @Test
    public void newTradeOfferTest() {
        assertTrue(modelFactory.newTradeOffer() instanceof TradeOffer);
        assertTrue(modelFactory.newTradeOffer() instanceof SharedAttributes);
    }

    @Test
    public void newPlayerTest() {
        assertTrue(modelFactory.newPlayer() instanceof Player);
        assertTrue(modelFactory.newPlayer() instanceof SharedAttributes);
    }

    @Test
    public void newUserTest() {
        assertTrue(modelFactory.newUser() instanceof User);
    }

    @Test
    public void newUserByNameTest() throws Exception {
        String name = "name";
        IUserDao loadUserFactory = new UserMock();
        IUser user = modelFactory.newUserByName(name, loadUserFactory);
        assertTrue(user instanceof IUser);
    }
}
