package simulation.state;

import org.junit.BeforeClass;
import org.junit.Test;
import persistance.dao.ICoachDao;
import persistance.dao.ILeagueDao;
import persistance.dao.IUserDao;
import presentation.ConsoleOutput;
import presentation.IConsoleOutput;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.CoachMock;
import simulation.mock.LeagueMock;
import simulation.mock.TrophyMock;
import simulation.mock.UserMock;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class TrophySystemTest {
    static IHockeyContext hockeyContext;
    static private ILeague league;
    static private IConsoleOutput consoleOutput;
    static private ITrophy trophy;

    @BeforeClass
    public static void setState() throws Exception {
        ILeagueDao factory = new LeagueMock();
        league = new League(4, factory);
        IUserDao factoryUser = new UserMock();
        IUser user = new User(1, factoryUser);
        user.setId(1);
        IHockeyContextFactory hockeyContextFactory = HockeyContextConcrete.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        hockeyContext.setUser(user);
        trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        league.setTrophy(trophy);
        consoleOutput = ConsoleOutput.getInstance();
    }


    @Test
    public void calculateJackAdamsAwardTest() throws Exception {
        List<ICoach> coachList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ICoachDao coachDao = new CoachMock();
            ICoach coach = new Coach(0, coachDao);
            coach.setId(i);
            if (i == 9) {
                coach.setName("Simran");
            }
            coach.setCoachingEffectiveness(i);
            coachList.add(coach);
        }
        TrophySystem trophySystem = new TrophySystem(hockeyContext, trophy);
        String winner = trophySystem.calculateJackAdamsAward(coachList);
        assertNotEquals(winner, null);
        assertTrue(winner == "Simran");
    }


    @Test
    public void showHistoricalTrophyListTest() {
        List<ITrophy> trophyList = new ArrayList<>();
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        for (int i = 0; i < 3; i++) {
            trophyList.add(trophy);
        }
        TrophySystem trophySystem = new TrophySystem(hockeyContext, trophy);
        trophySystem.showHistoricalTrophyList(trophyList);
        System.out.println(trophySystem.getClass());
        assertEquals(trophySystem.getClass().toString(), "class simulation.state.TrophySystem");
        assertNotEquals(trophySystem.getClass(), null);
    }


}
