package model;


import data.ILoadLeagueFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class HockeyContextTest {

    private static ILoadLeagueFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new LoadLeagueMock();
    }

    @Test
    public void startActionTest() {
//        HockeyContext hockeyContext = new HockeyContext();
//        assertEquals(hockeyContext.getHockeyState(),null );
    }

    @Test
    public void getLeagueTest() {
//        HockeyContext hockeyContext = new HockeyContext();
//        assertEquals(null,null );
    }

    @Test
    public void setLeagueTest() {

    }


}