package model;


import data.ILeagueFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class HockeyContextTest {

    private static ILeagueFactory factory;

    @BeforeClass
    public static void setFactoryObj(){
        factory = new LeagueMock();
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