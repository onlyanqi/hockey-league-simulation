package simulation.model;

import org.junit.Before;
import org.junit.Test;
import simulation.dao.*;

import static org.junit.Assert.assertTrue;

public class ModelFactoryTest {

    private IModelFactory modelFactory;
    private IDaoFactory daoFactory;

    @Before
    public void init() {
        modelFactory = ModelFactory.getInstance();
        daoFactory = DaoFactoryMock.getInstance();
    }

    @Test
    public void getInstanceTest() {
        IModelFactory modelFactory = ModelFactory.getInstance();
        assertTrue(modelFactory instanceof IModelFactory);
    }

    @Test
    public void createDivisionTest() {
        assertTrue(modelFactory.createDivision() instanceof IDivision);
    }

    @Test
    public void createDivisionWithIdTest() {
        assertTrue(modelFactory.createDivisionWithId(1) instanceof IDivision);
    }

    @Test
    public void createDivisionWithIdDaoTest() throws Exception {
        IDivisionDao divisionDao = daoFactory.createDivisionDao();
        assertTrue(modelFactory.createDivisionWithIdDao(1, divisionDao) instanceof IDivision);
    }

    @Test
    public void createConferenceTest() {
        assertTrue(modelFactory.createConference() instanceof IConference);
    }

    @Test
    public void createConferenceWithIdTest() {
        assertTrue(modelFactory.createConferenceWithId(1) instanceof IConference);
    }

    @Test
    public void createConferenceWithIdDaoTest() throws Exception {
        IConferenceDao conferenceDao = daoFactory.createConferenceDao();
        assertTrue(modelFactory.createConferenceWithIdDao(1, conferenceDao) instanceof IConference);
    }

    @Test
    public void createFreeAgentTest() {
        assertTrue(modelFactory.createFreeAgent() instanceof IFreeAgent);
    }

    @Test
    public void createFreeAgentWithIdTest() {
        assertTrue(modelFactory.createFreeAgentWithId(1) instanceof IFreeAgent);
    }

    @Test
    public void createFreeAgentWithIdDaoTest() throws Exception {
        IFreeAgentDao freeAgentDao = daoFactory.createFreeAgentDao();
        assertTrue(modelFactory.createFreeAgentWithIdDao(1, freeAgentDao) instanceof IFreeAgent);
    }

    @Test
    public void createLeagueTest() {
        assertTrue(modelFactory.createLeague() instanceof ILeague);
    }

    @Test
    public void createLeagueWithIdTest() {
        assertTrue(modelFactory.createLeagueWithId(1) instanceof ILeague);
    }

    @Test
    public void createLeagueWithIdDaoTest() throws Exception {
        ILeagueDao leagueDao = daoFactory.createLeagueDao();
        assertTrue(modelFactory.createLeagueWithIdDao(1, leagueDao) instanceof ILeague);
    }

    @Test
    public void createLeagueNameUserIdTest() throws Exception {
        String leagueName = "Name";
        int userId = 1;
        ILeagueDao loadLeagueFactory = daoFactory.createLeagueDao();
        ILeague league = modelFactory.createLeagueFromNameAndUserId(leagueName, userId, loadLeagueFactory);
        assertTrue(league instanceof League);
    }

    @Test
    public void createSeasonTest() {
        assertTrue(modelFactory.createSeason() instanceof ISeason);
    }

    @Test
    public void createSeasonWithIdDaoTest() throws Exception {
        ISeasonDao seasonDao = daoFactory.createSeasonDao();
        assertTrue(modelFactory.createSeasonWithIdDao(1, seasonDao) instanceof ISeason);
    }

    @Test
    public void createTeamTest() {
        assertTrue(modelFactory.createTeam() instanceof ITeam);
    }

    @Test
    public void createTeamWithIdTest() {
        assertTrue(modelFactory.createTeamWithId(1) instanceof ITeam);
    }

    @Test
    public void createTeamWithIdDaoTest() throws Exception {
        ITeamDao teamDao = daoFactory.createTeamDao();
        assertTrue(modelFactory.createTeamWithIdDao(1, teamDao) instanceof ITeam);
    }

    @Test
    public void createTeamByNameTest() throws Exception {
        String name = "Name";
        ITeamDao loadTeamFactory = daoFactory.createTeamDao();
        ITeam team = modelFactory.createTeamByName(name, loadTeamFactory);
        assertTrue(team instanceof ITeam);
    }

    @Test
    public void createTradingTest() {
        assertTrue(modelFactory.createTrading() instanceof ITrading);
        assertTrue(modelFactory.createTrading() instanceof SharedAttributes);
    }

    @Test
    public void createTradingWithIdDaoTest() throws Exception {
        ITradingDao tradingDao = daoFactory.createTradingDao();
        assertTrue(modelFactory.createTradingWithIdDao(1, tradingDao) instanceof ITrading);
        assertTrue(modelFactory.createTradingWithIdDao(1, tradingDao) instanceof SharedAttributes);
    }

    @Test
    public void createTradeOfferTest() {
        assertTrue(modelFactory.createTradeOffer() instanceof ITradeOffer);
        assertTrue(modelFactory.createTradeOffer() instanceof SharedAttributes);
    }

    @Test
    public void createTradeOfferWithIdDaoTest() throws Exception {
        ITradeOfferDao tradeOfferDao = daoFactory.createTradeOfferDao();
        assertTrue(modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao) instanceof ITradeOffer);
        assertTrue(modelFactory.createTradeOfferWithIdDao(1, tradeOfferDao) instanceof SharedAttributes);
    }

    @Test
    public void createPlayerTest() {
        assertTrue(modelFactory.createPlayer() instanceof IPlayer);
        assertTrue(modelFactory.createPlayer() instanceof SharedAttributes);
    }

    @Test
    public void createPlayerWithIdTest() {
        assertTrue(modelFactory.createPlayerWithId(1) instanceof IPlayer);
        assertTrue(modelFactory.createPlayerWithId(1) instanceof SharedAttributes);
    }

    @Test
    public void createPlayerWithIdDaoTest() throws Exception {
        IPlayerDao playerDao = daoFactory.createPlayerDao();
        assertTrue(modelFactory.createPlayerWithIdDao(1, playerDao) instanceof IPlayer);
        assertTrue(modelFactory.createPlayerWithIdDao(1, playerDao) instanceof SharedAttributes);
    }

    @Test
    public void createUserTest() {
        assertTrue(modelFactory.createUser() instanceof IUser);
    }

    @Test
    public void createUserWithIdTest() {
        assertTrue(modelFactory.createUserWithId(1) instanceof IUser);
    }

    @Test
    public void createUserWithIdDaoTest() throws Exception {
        IUserDao userDao = daoFactory.createUserDao();
        assertTrue(modelFactory.createUserWithIdDao(1, userDao) instanceof IUser);
    }

    @Test
    public void createUserByNameTest() throws Exception {
        String name = "name";
        IUserDao loadUserFactory = daoFactory.createUserDao();
        IUser user = modelFactory.createUserByName(name, loadUserFactory);
        assertTrue(user instanceof IUser);
    }
}
