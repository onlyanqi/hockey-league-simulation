package simulation.model;

import org.junit.Before;
import org.junit.Test;
import persistance.dao.ILeagueDao;
import persistance.dao.ITeamDao;
import persistance.dao.IUserDao;
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
        assertTrue(modelFactory.createDivision() instanceof Division);
    }

    @Test
    public void newConferenceTest() {
        assertTrue(modelFactory.createConference() instanceof Conference);
    }

    @Test
    public void newFreeAgentTest() {
        assertTrue(modelFactory.createFreeAgent() instanceof FreeAgent);
    }

    @Test
    public void newLeagueTest() {
        assertTrue(modelFactory.createLeague() instanceof League);
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
        assertTrue(modelFactory.createSeason() instanceof Season);
    }

    @Test
    public void newTeamTest() {
        assertTrue(modelFactory.createTeam() instanceof Team);
    }

    @Test
    public void newTeamByNameTest() throws Exception {
        String name = "Name";
        ITeamDao loadTeamFactory = new TeamMock();
        ITeam team = modelFactory.createTeamByName(name, loadTeamFactory);
        assertTrue(team instanceof Team);
    }

    @Test
    public void newTradingTest() {
        assertTrue(modelFactory.createTrading() instanceof Trading);
        assertTrue(modelFactory.createTrading() instanceof SharedAttributes);
    }

    @Test
    public void newTradeOfferTest() {
        assertTrue(modelFactory.createTradeOffer() instanceof TradeOffer);
        assertTrue(modelFactory.createTradeOffer() instanceof SharedAttributes);
    }

    @Test
    public void newPlayerTest() {
        assertTrue(modelFactory.createPlayer() instanceof Player);
        assertTrue(modelFactory.createPlayer() instanceof SharedAttributes);
    }

    @Test
    public void newUserTest() {
        assertTrue(modelFactory.createUser() instanceof User);
    }

    @Test
    public void newUserByNameTest() throws Exception {
        String name = "name";
        IUserDao loadUserFactory = new UserMock();
        IUser user = modelFactory.createUserByName(name, loadUserFactory);
        assertTrue(user instanceof IUser);
    }
}
