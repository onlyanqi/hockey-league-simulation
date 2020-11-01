package simulation.state;
import db.data.ILeagueFactory;
import db.data.IPlayerFactory;
import db.data.ITrainingFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.mock.LeagueMock;
import simulation.mock.PlayerMock;
import simulation.mock.TrainingMock;
import simulation.model.*;

import static org.junit.Assert.*;

public class TrainingStateTest {
    private static ITrainingState trainingState;
    private static ILeagueFactory leagueFactory;
    private static HockeyContext hockeyContext;

    @BeforeClass
    public static void setFactoryObject() throws Exception {
        hockeyContext = new HockeyContext(new User(1));

        trainingState = new TrainingState(hockeyContext);
        leagueFactory = new LeagueMock();
    }

    @Test
    public void statIncreaseCheck() throws Exception {
        League league = new League(4,leagueFactory);
        hockeyContext.getUser().setLeague(league);
        League oldLeague = league;
        trainingState.statIncreaseCheck(league);
        assertEquals(league,oldLeague);
        assertNotEquals(league,null);
    }

    @Test
    public void statIncreaseCheckForPlayerTest() throws Exception {
        League league = new League(4,leagueFactory);
        hockeyContext.getUser().setLeague(league);
        League oldLeague = league;
        Player player = league.getConferenceList().get(1).getDivisionList().get(1).getTeamList().get(0).getPlayerList().get(1);
        Coach headCoach = league.getConferenceList().get(1).getDivisionList().get(1).getTeamList().get(0).getCoach();
        Player oldPlayer = player;
        Coach oldHeadCoach = headCoach;
        trainingState.statIncreaseCheckForPlayer(player,headCoach);
        assertEquals(oldPlayer,player);
        assertEquals(oldHeadCoach,headCoach);
        assertNotEquals(player,null);
        assertNotEquals(headCoach,null);
    }

    @Test
    public void isStrengthInRangeAfterIncreaseTest(){
        assertTrue(trainingState.isStrengthInRangeAfterIncrease(20));
        assertFalse(trainingState.isStrengthInRangeAfterIncrease(21));
        assertFalse(trainingState.isStrengthInRangeAfterIncrease(0));
        assertFalse(trainingState.isStrengthInRangeAfterIncrease(-2));
        assertTrue(trainingState.isStrengthInRangeAfterIncrease(15));
    }

    @Test
    public void exitTest() throws Exception {

    //   assertTrue(trainingState.exit().getClass())

    }
}
