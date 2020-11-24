package simulation.model;

import db.data.IConferenceDao;
import db.data.IDivisionDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcreteMock;
import simulation.factory.IConferenceFactory;
import simulation.factory.IDivisionFactory;
import simulation.factory.IHockeyContextFactory;
import simulation.mock.DivisionMock;
import simulation.state.IHockeyContext;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ConferenceTest {

    private static IConferenceFactory conferenceFactory;
    private static IConferenceDao conferenceDao;
    private static IHockeyContextFactory hockeyContextFactory;
    private static IHockeyContext hockeyContext;
    private static IDivisionFactory divisionFactory;

    @BeforeClass
    public static void setFactoryObj() {
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        conferenceFactory = hockeyContext.getConferenceFactory();
        conferenceDao = conferenceFactory.newConferenceDao();
        divisionFactory = hockeyContext.getDivisionFactory();
    }

    @Test
    public void defaultConstructorTest() {
        IConference conference = conferenceFactory.newConference();
        assertNotEquals(conference.getId(), 0);
    }

    @Test
    public void conferenceTest() {
        IConference conference = conferenceFactory.newConferenceWithId(1);
        assertEquals(conference.getId(), 1);
    }

    @Test
    public void conferenceFactoryTest() throws Exception {
        IConference conference = conferenceFactory.newConferenceWithIdDao(1, conferenceDao);
        assertEquals(conference.getId(), 1);
        assertEquals(conference.getName(), "Conference1");

        conference = conferenceFactory.newConferenceWithIdDao(2, conferenceDao);
        assertNull(conference.getName());
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        IConference conference = conferenceFactory.newConferenceWithIdDao(1, conferenceDao);
        assertTrue(conference.getLeagueId() == (1));
    }

    @Test
    public void setLeagueIdTest() {
        IConference conference = conferenceFactory.newConference();
        int leagueId = 1;
        conference.setLeagueId(leagueId);
        assertTrue(conference.getLeagueId() == leagueId);
    }

    @Test
    public void getDivisionListTest() throws Exception {
        IConference conference = conferenceFactory.newConferenceWithIdDao(1, conferenceDao);
        List<IDivision> divisionList = conference.getDivisionList();
        assertNotNull(divisionList);

        assertTrue(divisionList.get(0).getId() == (1));
        assertTrue(divisionList.get(1).getId() == (2));
        assertTrue(divisionList.get(0).getName().equals("Division1"));
        assertNull(divisionList.get(1).getName());
    }

    @Test
    public void setDivisionListTest() throws Exception {
        IDivisionDao divisionDao = divisionFactory.newDivisionDao();
        List<IDivision> divisionList = new ArrayList<>();
        IDivision division = divisionFactory.newDivisionWithIdDao(1, divisionDao);
        divisionList.add(division);
        division = divisionFactory.newDivisionWithIdDao(2, divisionDao);
        divisionList.add(division);

        IConference conference = conferenceFactory.newConference();
        conference.setDivisionList(divisionList);

        assertTrue(conference.getDivisionList().get(0).getId() == (1));
        assertTrue(conference.getDivisionList().get(1).getId() == (2));
        assertTrue(conference.getDivisionList().get(0).getName().equals("Division1"));
        assertNull(conference.getDivisionList().get(1).getName());
    }

    @Test
    public void addConferenceTest() throws Exception {
        IConference conference = conferenceFactory.newConference();
        conference.setId(1);
        conference.setName("Conference1");
        conference.addConference(conferenceDao);
        assertTrue(1 == conference.getId());
        assertTrue("Conference1".equals(conference.getName()));
    }

    @Test
    public void loadDivisionListByConferenceIdTest() throws Exception {
        IConference conference = conferenceFactory.newConferenceWithId(1);
        IDivisionDao divisionDao = divisionFactory.newDivisionDao();
        conference.loadDivisionListByConferenceId(divisionDao);

        assertTrue(conference.getDivisionList().get(0).getId() == (1));
        assertTrue(conference.getDivisionList().get(1).getId() == (2));
        assertTrue(conference.getDivisionList().get(0).getName().equals("Division1"));
        assertNull(conference.getDivisionList().get(1).getName());
    }

}
