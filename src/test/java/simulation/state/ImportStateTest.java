package simulation.state;

import db.data.ILeagueFactory;
import simulation.mock.LeagueMock;
import simulation.model.User;
import simulation.mock.JSONControllerMock;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.BeforeClass;

import static org.junit.Assert.*;


public class ImportStateTest {

    private static ILeagueFactory factory;
    static User user ;
    IHockeyState state ;

    @BeforeClass
    public static void setAll(){
        factory = new LeagueMock();
        user = new User();
        user.setName("user1");
        user.setId(1);

    }


    @Test
    public void processTest() throws Exception {
        HockeyContext hockeyContext = new HockeyContext();
        hockeyContext.setUser(user);

        assertEquals(hockeyContext.getUser().getId(),user.getId());
        assertEquals(hockeyContext.getUser().getName(),user.getName());



        JSONObject leagueObject = JSONControllerMock.getJSON(1);
        state = new ImportState(hockeyContext,leagueObject);

        state.process();
        System.out.println(hockeyContext.getUser().getLeague().getName());

        assertNotNull(hockeyContext.getUser().getLeague());
        assertEquals(hockeyContext.getUser().getLeague().getName() , "Dalhousie Hockey League");
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getAging().getMaximumAge(),50);
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getGameResolver().getRandomWinChance(),(Double)0.1);
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getInjury().getInjuryDaysLow(),1);
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getTraining().getDaysUntilStatIncreaseCheck(),100);
        assertEquals(hockeyContext.getUser().getLeague().getGamePlayConfig().getTrading().getMaxPlayersPerTrade(),2);
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getName(),"Eastern Conference");
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getName(),"Atlantic");
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getName(),"Boston");
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getCoach().getShooting(),(Double)0.8);
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getPlayerList().get(0).getName(),"Player One");
        assertEquals(hockeyContext.getUser().getLeague().getConferenceList().get(0).getDivisionList().get(0).getTeamList().get(0).getPlayerList().get(0).getChecking(),12);
        assertEquals(hockeyContext.getUser().getLeague().getFreeAgentList().get(2).getName(),"Agent Three");
        assertEquals(hockeyContext.getUser().getLeague().getFreeAgentList().get(1).getChecking(),10);
        assertEquals(hockeyContext.getUser().getLeague().getCoachList().get(1).getName(),"Frank Smith");
        assertEquals(hockeyContext.getUser().getLeague().getCoachList().get(2).getSkating(),(Double)1.0);
        assertEquals(hockeyContext.getUser().getLeague().getManagerList().get(0).getName(),"Karen Potam");


    }

    @Test
    public void exitTest(){
        //assertEquals(hockeyState.exit(),null);
    }

}