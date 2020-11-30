package simulation.model;

import org.junit.BeforeClass;
import org.junit.Test;
import simulation.dao.DaoFactoryMock;
import persistance.dao.IDaoFactory;
import persistance.dao.ILeagueDao;
import persistance.dao.IPlayerDao;
import simulation.mock.LeagueMock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    private static IPlayerDao playerDao;
    private static ILeagueDao leagueDao;
    private static IModelFactory modelFactory;
    private static IDaoFactory daoFactory;

    @BeforeClass
    public static void setFactoryObj() {
        daoFactory = DaoFactoryMock.getInstance();
        playerDao = daoFactory.newPlayerDao();
        leagueDao = daoFactory.newLeagueDao();
        modelFactory = ModelFactory.getInstance();
    }

    @Test
    public void defaultConstructorTest() {
        IPlayer player = new Player();
        assertNotEquals(player.getId(), 0);
    }

    @Test
    public void playerTest() {
        IPlayer player = new Player(1);
        assertEquals(player.getId(), 1);
    }

    @Test
    public void playerNullTest() {
        IPlayer player = new Player((Player) null);
        assertEquals(player.getId(), 0);
    }

    @Test
    public void playerFactoryTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertEquals(player.getId(), 1);
        assertEquals(player.getName(), "Player1");

        player = new Player(34, playerDao);
        assertNull(player.getName());
    }

    @Test
    public void getAgeTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertEquals(player.getAge(), 27);
    }

    @Test
    public void setAgeTest() {
        IPlayer player = new Player();
        int age = 15;
        player.setAge(age);
        assertEquals(player.getAge(), age);
    }

    @Test
    public void getPositionTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertEquals(player.getPosition(), Position.FORWARD);
    }

    @Test
    public void setPositionTest() {
        IPlayer player = new Player();
        Position position = Position.GOALIE;
        player.setPosition(position);
        assertEquals(player.getPosition(), position);
    }

    @Test
    public void getTeamIdTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertEquals(player.getTeamId(), (1));
    }

    @Test
    public void setTeamIdTest() {
        IPlayer player = new Player();
        int teamId = 1;
        player.setTeamId(teamId);
        assertEquals(player.getTeamId(), teamId);
    }

    @Test
    public void isCaptainTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertTrue(player.isCaptain());
    }

    @Test
    public void setCaptainTest() {
        IPlayer player = new Player();
        boolean isCaptain = true;
        player.setCaptain(true);
        assertTrue(player.isCaptain());
    }

    @Test
    public void addPlayerTest() throws Exception {
        IPlayer player = new Player();
        player.setId(1);
        player.setName("Player1");
        player.addPlayer(playerDao);
        assertEquals(1, player.getId());
        assertEquals("Player1", player.getName());
    }

    @Test
    public void getFreeAgentIdTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertEquals(player.getFreeAgentId(), (1));
    }

    @Test
    public void setFreeAgentIdTest() {
        IPlayer player = new Player();
        int freeAgentId = 1;
        player.setFreeAgentId(freeAgentId);
        assertEquals(player.getFreeAgentId(), freeAgentId);
    }

    @Test
    public void getInjuredTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertTrue(player.getInjured());
    }

    @Test
    public void getInjuredFalseTest() throws Exception {
        IPlayer player = new Player(2, playerDao);
        assertFalse(player.getInjured());
    }

    @Test
    public void setInjuredTest() {
        IPlayer player = new Player();
        player.setInjured(false);
        assertFalse(player.getInjured());
    }

    @Test
    public void getInjuryStartDateTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertEquals(player.getInjuryStartDate(), LocalDate.now());
    }

    @Test
    public void setInjuryStartDateTest() {
        IPlayer player = new Player();
        player.setInjuryStartDate(LocalDate.now());
        assertEquals(player.getInjuryStartDate(), LocalDate.now());
    }

    @Test
    public void getInjuryDatesRangeTest() throws Exception {
        IPlayer player = new Player(1, playerDao);
        assertEquals(player.getInjuryDatesRange(), 80);
    }

    @Test
    public void setInjuryDatesRangeTest() {
        IPlayer player = new Player();
        player.setInjuryDatesRange(100);
        assertEquals(player.getInjuryDatesRange(), 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenAgeIsNegativeNumber() {
        IPlayer player = new Player();
        player.setAge(-20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenSkatingIsOutOfRange() {
        IPlayer player = new Player();
        player.setSkating(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenShootingIsOutOfRange() {
        IPlayer player = new Player();
        player.setShooting(22);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenCheckingIsOutOfRange() {
        IPlayer player = new Player();
        player.setChecking(78);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionWhenSavingIsOutOfRange() {
        IPlayer player = new Player();
        player.setSaving(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setInvalidPositionTest() throws Exception {
        IPlayer player = new Player(32, playerDao);
    }

    @Test
    public void retirementCheckTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        ILeague league = new League(1, leagueFactory);
        Player player = new Player(20, playerDao);
        boolean retired = player.retirementCheck(league);
        assertTrue(retired);
    }


    @Test
    public void calculateAgeTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        ILeague league = new League(1, leagueFactory);
        Player player = new Player(31, playerDao);
        player.calculateAge(league);
        assertEquals(player.getAge(), 23);
    }

    @Test
    public void injuryCheckTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        ILeague league = new League(1, leagueFactory);
        IPlayer player = new Player(2, playerDao);
        assertFalse(player.getInjured());
        player.injuryCheck(league);
        assertTrue(player.getInjured());
    }

    @Test
    public void injuryCheckInjuredTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        ILeague league = new League(1, leagueFactory);
        IPlayer player = new Player(1, playerDao);
        player.injuryCheck(league);
        assertTrue(player.getInjured());
    }

    @Test
    public void injuryCheckNUllTest() throws Exception {
        IPlayer player = new Player(2, playerDao);
        player.injuryCheck(null);
        assertFalse(player.getInjured());
    }

    @Test
    public void agingInjuryRecoveryTest() throws Exception {
        IPlayer player = new Player(12, playerDao);
        ILeagueDao leagueFactory = new LeagueMock();
        ILeague league = new League(1, leagueFactory);
        assertTrue(player.getInjured());
        assertNotNull(player.getInjuryStartDate());
        assertEquals(player.getInjuryDatesRange(), 80);
        player.agingInjuryRecovery(league);
        assertFalse(player.getInjured());
        assertNull(player.getInjuryStartDate());
        assertEquals(player.getInjuryDatesRange(), 0);
    }

    @Test
    public void agingInjuryRecoveryNullTest() throws Exception {
        IPlayer player = new Player(12, playerDao);
        player.agingInjuryRecovery(null);
        assertTrue(player.getInjured());
        assertNotNull(player.getInjuryStartDate());
        assertEquals(player.getInjuryDatesRange(), 80);
    }

    @Test
    public void findBestReplacementTest() throws Exception {
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        List<IPlayer> playerList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Player player = new Player(i, playerDao);
            playerList.add(player);
        }
        List<IPlayer> freePlayerList = league.getFreeAgent().getPlayerList();
        assertEquals(playerList.get(19).getName(), "Player20");
        IPlayer player1 = playerList.get(0);
        assertEquals(playerList.get(0).getName(), "Player1");
        player1.findBestReplacement(playerList, freePlayerList);
        assertNotEquals(playerList.get(0).getName(), "Player1");
        assertEquals(playerList.get(19).getName(), "Player6");
    }

    @Test
    public void getRelativeStrengthTest() throws Exception {
        IPlayer player = modelFactory.createPlayerWithIdDao(1, playerDao);
        assertTrue(player.getRelativeStrength() == (7.8));
    }

    @Test
    public void setRelativeStrengthTest() {
        IPlayer player = modelFactory.createPlayer();
        player.setSkating(15);
        player.setShooting(18);
        player.setChecking(12);
        player.setPosition(Position.FORWARD);
        player.setStrength();
        player.setRelativeStrength();
        assertTrue(player.getRelativeStrength() == (7.8));
    }

    @Test
    public void statDecayCheckTest() throws Exception {
        IPlayer player = modelFactory.createPlayerWithIdDao(33, playerDao);
        assertEquals(player.getSkating(), 15);
        assertEquals(player.getShooting(), 18);
        assertEquals(player.getChecking(), 12);
        assertEquals(player.getSaving(), 1);
        ILeague league = modelFactory.createLeagueWithIdDao(1,leagueDao);
        player.statDecayCheck(league);
        assertEquals(player.getSkating(), 15-1);
        assertEquals(player.getShooting(), 18-1);
        assertEquals(player.getChecking(), 12-1);
        assertEquals(player.getSaving(), 1);
    }

}
