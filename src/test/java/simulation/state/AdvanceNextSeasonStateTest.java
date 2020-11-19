package simulation.state;

import db.data.*;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.PlayerMock;
import simulation.mock.TeamMock;
import simulation.mock.UserMock;
import simulation.model.League;
import simulation.model.Player;
import simulation.model.User;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AdvanceNextSeasonStateTest {

    private static IPlayerFactory playerFactory;
    private static IUserFactory userFactory;
    private static IHockeyContext hockeyContext;

    @BeforeClass
    public static void init() throws Exception {
        playerFactory = new PlayerMock();
        userFactory = new UserMock();
        IHockeyContextFactory hockeyContextFactory = new HockeyContextConcrete();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
    }

    @Test
    public void findReplacementTest() throws Exception {
        AdvanceNextSeasonState state = new AdvanceNextSeasonState(hockeyContext);
        ILeagueFactory leagueFactory = new LeagueMock();
        League league = new League(1, leagueFactory);
        List<Player> playerList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            Player player = new Player(i, playerFactory);
            playerList.add(player);
        }
        assertEquals(playerList.get(19).getName(), "Player20");
        state.findReplacement(playerList, Player.Position.forward, 0);
        assertNotEquals(playerList.get(20).getName(), "Player20");
        assertEquals(playerList.get(20).getName(), "Player6");
    }
}
