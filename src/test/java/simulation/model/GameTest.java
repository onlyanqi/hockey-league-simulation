package simulation.model;

import simulation.dao.IGameDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.GameMock;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class GameTest {
    private static IGameDao iGameDao;

    @BeforeClass
    public static void setFactoryObj() {
        iGameDao = new GameMock();
    }

    @Test
    public void defaultConstructorTest() {
        Game game = new Game();
        assertNotEquals(game.getId(), 0);
    }

    @Test
    public void teamScoreFactory() throws Exception {
        Game game = new Game(1, iGameDao);
        assertEquals(game.getId(), 1);

        Game game1 = new Game(3, iGameDao);
        assertEquals(game1.getId(), 3);
        assertNull(game1.getWinner());
    }

    @Test
    public void getIdTest() throws Exception {
        Game game = new Game(1, iGameDao);
        assertTrue(game.getId() == 1);

        Game game2 = new Game(3, iGameDao);
        assertTrue(game2.getId() == 3);
    }

    @Test
    public void setIdTest() throws Exception {
        Game game = new Game();
        game.setId(1);
        assertTrue(game.getId() == 1);

        Game game2 = new Game();
        assertFalse(game2.getId() == 0);
    }

    @Test
    public void getTeam1Test() throws Exception {
        Game game = new Game(1, iGameDao);
        assertTrue(game.getTeam1().equals("Gander Milk Cartons"));
    }

    @Test
    public void setTeam1Test() throws Exception {
        Game game = new Game();
        game.setTeam1("Team1");
        assertTrue(game.getTeam1().equals("Team1"));

        Game game2 = new Game();
        game2.setTeam1(null);
        assertNull(game2.getTeam1());
    }

    @Test
    public void getDateTest() throws Exception {
        Game game = new Game(1, iGameDao);
        assertTrue(game.getDate().equals(LocalDate.of(2020, Month.OCTOBER, 30)));

        Game game2 = new Game(3, iGameDao);
        assertTrue(game2.getDate().equals(LocalDate.of(2020, Month.NOVEMBER, 03)));
    }

    @Test
    public void getPlayedTest() throws Exception {
        Game game = new Game(1, iGameDao);
        assertTrue(game.getDate().equals(LocalDate.of(2020, Month.OCTOBER, 30)));

        Game game2 = new Game(3, iGameDao);
        assertTrue(game2.getDate().equals(LocalDate.of(2020, Month.NOVEMBER, 03)));
    }

    @Test
    public void getWinnerTest() throws Exception {
        Game game2 = new Game(3, iGameDao);
        assertNull(game2.getWinner());

    }

    @Test
    public void isGameUnPlayedTest() throws Exception {
        Game game = new Game(3, iGameDao);
        assertTrue(game.isGameUnPlayed());

        Game game2 = new Game(1, iGameDao);
        assertFalse(game2.isGameUnPlayed());
    }

    @Test
    public void fromStringTest() {
        assertTrue(Game.fromString("TEAM1").equals(Result.TEAM1));
        assertFalse(Game.fromString("TEAM1").equals(Result.TEAM2));
    }

}
