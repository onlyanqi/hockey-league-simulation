package simulation.model;

import simulation.dao.IGameDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.GameMock;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameScheduleTest {
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
    public void getIdTest() throws Exception {
        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(1);
        assertTrue(gameSchedule.getId() == 1);

    }

    @Test
    public void setIdTest() throws Exception {
        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(2);
        assertTrue(gameSchedule.getId() == 2);
    }

    @Test
    public void getGamesOnDateTest() throws Exception {

        Game game = new Game(2, iGameDao);
        Game game2 = new Game(1, iGameDao);
        List<IGame> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);

        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(3);
        gameSchedule.setGameList(gameList);
        assertNotEquals(gameSchedule.getGamesOnDate(game.getDate()).size(), 0);
    }

    @Test
    public void doGamesExistAfterDateTest() throws Exception {
        Game game = new Game(2, iGameDao);
        Game game2 = new Game(1, iGameDao);
        List<IGame> gameList = new ArrayList<>();
        gameList.add(game);
        gameList.add(game2);

        GameSchedule gameSchedule = new GameSchedule();
        gameSchedule.setId(3);
        gameSchedule.setGameList(gameList);
        assertFalse(gameSchedule.doGamesDoesNotExistAfterDate(LocalDate.of(2020, Month.OCTOBER, 29)));
    }

}
