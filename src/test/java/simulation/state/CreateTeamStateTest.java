package simulation.state;

import db.data.ILeagueFactory;
import db.data.ITeamFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.model.League;
import simulation.model.Team;
import simulation.model.User;

public class CreateTeamStateTest {

    static HockeyContext hockeyContext;
    static IHockeyState hockeyState;
    static IHockeyState hockeyState2;
    static User user ;
    static League league;
    private static Team team;

    private static ILeagueFactory factory;
    private static ITeamFactory factory2;

    @BeforeClass
    public static void setState(){
//
//        user = new User();
//        user.setId(2);
//        factory = new LoadLeagueMock();
//        factory2 = new LoadTeamMock();
//
//        hockeyContext = new HockeyContext(user);
//        hockeyState = new CreateTeamState(hockeyContext);
//
//        hockeyState2 = new CreateTeamState(hockeyContext);



    }

    @Test
    public void entryTest() throws Exception {

//        league = new League(1, factory);
//        team = new Team(3, factory2);
//
//        assertTrue(true);

    }

    @Test
    public void processTest() throws Exception {


        //hockeyContext.setLeague(league);
        //hockeyState.process();

    }

    @Test
    public void exitTest(){

        //assertTrue(hockeyState.exit() instanceof PlayerChoiceState);
    }
}
