package simulation.state;

import db.data.ILeagueFactory;
import db.data.IUserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.LeagueMock;
import simulation.mock.UserMock;
import simulation.model.Coach;
import simulation.model.League;
import simulation.model.Player;
import simulation.model.User;

import static org.junit.Assert.*;

public class TrainingStateTest {
    private static ITrainingState trainingState;
    private static ILeagueFactory leagueFactory;
    private static IHockeyContext hockeyContext;
    private static IUserFactory userFactory;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void setFactoryObject() throws Exception {
        hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        userFactory = new UserMock();
        User user = new User(1, userFactory);
        hockeyContext.setUser(user);
        trainingState = new TrainingState(hockeyContext);
        leagueFactory = new LeagueMock();
    }

    @Test
    public void actionTest() throws Exception {
        League league = new League(4, leagueFactory);
        hockeyContext.getUser().setLeague(league);
        TrainingState state = new TrainingState(hockeyContext);
        assertNull(state.action());

        league.getGamePlayConfig().getTraining().setDaysUntilStatIncreaseCheck(102);

        assertTrue(state.action() instanceof ISimulateState);

    }

    @Test
    public void statIncreaseCheck() throws Exception {
        League league = new League(4, leagueFactory);
        hockeyContext.getUser().setLeague(league);
        League oldLeague = league;
        trainingState.statIncreaseCheck(league);
        assertEquals(league, oldLeague);
        assertNotEquals(league, null);
        League nullLeague = null;
        trainingState.statIncreaseCheck(nullLeague);
        assertEquals(nullLeague, null);
    }

    @Test
    public void statIncreaseCheckForPlayerTest() throws Exception {
        League league = new League(4, leagueFactory);
        hockeyContext.getUser().setLeague(league);
        League oldLeague = league;
        Player player = league.getConferenceList().get(1).getDivisionList().get(1).getTeamList().get(0).getPlayerList().get(1);
        Coach headCoach = league.getConferenceList().get(1).getDivisionList().get(1).getTeamList().get(0).getCoach();
        Player oldPlayer = player;
        Coach oldHeadCoach = headCoach;
        trainingState.statIncreaseCheckForPlayer(player, headCoach);
        assertEquals(oldPlayer, player);
        assertEquals(oldHeadCoach, headCoach);
        assertNotEquals(player, null);
        assertNotEquals(headCoach, null);
        player = null;
        headCoach = null;
        trainingState.statIncreaseCheckForPlayer(player, headCoach);
        assertEquals(player, null);
        assertEquals(headCoach, null);
    }

    @Test
    public void isStrengthInRangeAfterIncreaseTest() {
        assertTrue(trainingState.isStrengthInRangeAfterIncrease(20));
        assertFalse(trainingState.isStrengthInRangeAfterIncrease(21));
        assertFalse(trainingState.isStrengthInRangeAfterIncrease(0));
        assertFalse(trainingState.isStrengthInRangeAfterIncrease(-2));
        assertTrue(trainingState.isStrengthInRangeAfterIncrease(15));
    }

}
