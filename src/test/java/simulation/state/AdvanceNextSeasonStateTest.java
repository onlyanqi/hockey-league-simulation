package simulation.state;

import simulation.dao.ILeagueDao;
import simulation.dao.IPlayerDao;
import simulation.dao.IUserDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.PlayerMock;
import simulation.mock.UserMock;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AdvanceNextSeasonStateTest {

    private static IPlayerDao playerFactory;
    private static IUserDao userFactory;
    private static IHockeyContext hockeyContext;

    @BeforeClass
    public static void init() throws Exception {
        playerFactory = new PlayerMock();
        userFactory = new UserMock();
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void findBestReplacementTest() throws Exception {
        AdvanceNextSeasonState state = new AdvanceNextSeasonState(hockeyContext);
        ILeagueDao leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        List<IPlayer> playerList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Player player = new Player(i, playerFactory);
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
}
