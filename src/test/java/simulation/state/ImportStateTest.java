package simulation.state;

import db.data.ILeagueFactory;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.AgingConcrete;
import simulation.factory.HockeyContextConcrete;
import simulation.factory.IAgingFactory;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.JSONControllerMock;
import simulation.mock.LeagueMock;
import simulation.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;


public class ImportStateTest {

    private static User user;
    private static ILeagueFactory factory;
    private IHockeyState state;
    private static IHockeyContextFactory hockeyContextFactory;

    @BeforeClass
    public static void setAll() {
        factory = new LeagueMock();
        user = new User();
        user.setName("user1");
        user.setId(1);
        hockeyContextFactory = new HockeyContextConcrete();
    }


    @Test
    public void processTest() throws Exception {
        IHockeyContext hockeyContext = hockeyContextFactory.newHockeyContext();
        hockeyContext.setUser(user);
        IAgingFactory agingFactory = new AgingConcrete();
        hockeyContext.setAgingFactory(agingFactory);
        assertEquals(hockeyContext.getUser().getId(), user.getId());
        assertEquals(hockeyContext.getUser().getName(), user.getName());

        JSONObject leagueObject = JSONControllerMock.getJSON(1);
        state = new ImportState(hockeyContext, leagueObject);

        state.process();
        System.out.println(hockeyContext.getUser().getLeague().getName());

        assertNotNull(hockeyContext.getUser().getLeague());
        assertEquals(hockeyContext.getUser().getLeague().getName(), "Dalhousie Hockey League");
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getAging().getMaximumAge(), 50);
        //assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getGameResolver().getRandomWinChance(), (Double) 0.1);
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getInjury().getInjuryDaysLow(), 1);
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getTraining().getDaysUntilStatIncreaseCheck(), 100);
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getTrading().getMaxPlayersPerTrade(), 2);
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getName(), "Eastern Conference");
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getName(), "Atlantic");
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getName(), "Boston");
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getCoach().getShooting(), (Double) 0.8);
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getPlayerList().size(), 30);
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getActivePlayerList().size(), 20);
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getInactivePlayerList().size(), 10);
        assertEquals(hockeyContext.getUser().getLeague().getFreeAgent().getPlayerList().get(2).getName(), "Agent 2");
        assertEquals(hockeyContext.getUser().getLeague().getFreeAgent().getPlayerList().get(1).getChecking(), 11);
        assertEquals(hockeyContext.getUser().getLeague().getCoachList().get(1).getName(), "Frank Smith");
        assertEquals(hockeyContext.getUser().getLeague().getCoachList().get(2).getSkating(), (Double) 1.0);
        assertEquals(hockeyContext.getUser().getLeague().getManagerList().get(0).getName(), "M");


    }
}