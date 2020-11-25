package simulation.model;

import simulation.dao.IConferenceDao;
import simulation.dao.IDaoFactory;
import simulation.dao.IDivisionDao;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.factory.HockeyContextConcreteMock;
import simulation.factory.IHockeyContextFactory;
import simulation.state.IHockeyContext;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ConferenceTest {

    private static IModelFactory modelFactory;
    private static IDaoFactory daoFactory;
    private static IConferenceDao conferenceDao;
    private static IHockeyContextFactory hockeyContextFactory;
    private static IHockeyContext hockeyContext;

    @BeforeClass
    public static void setFactoryObj() {
        hockeyContextFactory = HockeyContextConcreteMock.getInstance();
        hockeyContext = hockeyContextFactory.newHockeyContext();
        modelFactory = hockeyContext.getModelFactory();
        daoFactory = hockeyContext.getDaoFactory();
        conferenceDao = daoFactory.newConferenceDao();
    }

    @Test
    public void defaultConstructorTest() {
        IConference conference = modelFactory.newConference();
        assertNotEquals(conference.getId(), 0);
    }

    @Test
    public void conferenceTest() {
        IConference conference = modelFactory.newConferenceWithId(1);
        assertEquals(conference.getId(), 1);
    }

    @Test
    public void conferenceFactoryTest() throws Exception {
        IConference conference = modelFactory.newConferenceWithIdDao(1, conferenceDao);
        assertEquals(conference.getId(), 1);
        assertEquals(conference.getName(), "Conference1");

        conference = modelFactory.newConferenceWithIdDao(2, conferenceDao);
        assertNull(conference.getName());
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        IConference conference = modelFactory.newConferenceWithIdDao(1, conferenceDao);
        assertTrue(conference.getLeagueId() == (1));
    }

    @Test
    public void setLeagueIdTest() {
        IConference conference = modelFactory.newConference();
        int leagueId = 1;
        conference.setLeagueId(leagueId);
        assertTrue(conference.getLeagueId() == leagueId);
    }

    @Test
    public void getDivisionListTest() throws Exception {
        IConference conference = modelFactory.newConferenceWithIdDao(1, conferenceDao);
        List<IDivision> divisionList = conference.getDivisionList();
        assertNotNull(divisionList);

        assertTrue(divisionList.get(0).getId() == (1));
        assertTrue(divisionList.get(1).getId() == (2));
        assertTrue(divisionList.get(0).getName().equals("Division1"));
        assertNull(divisionList.get(1).getName());
    }

    @Test
    public void setDivisionListTest() throws Exception {
        IDivisionDao divisionDao = daoFactory.newDivisionDao();
        List<IDivision> divisionList = new ArrayList<>();
        IDivision division = modelFactory.newDivisionWithIdDao(1, divisionDao);
        divisionList.add(division);
        division = modelFactory.newDivisionWithIdDao(2, divisionDao);
        divisionList.add(division);

        IConference conference = modelFactory.newConference();
        conference.setDivisionList(divisionList);

        assertTrue(conference.getDivisionList().get(0).getId() == (1));
        assertTrue(conference.getDivisionList().get(1).getId() == (2));
        assertTrue(conference.getDivisionList().get(0).getName().equals("Division1"));
        assertNull(conference.getDivisionList().get(1).getName());
    }

    @Test
    public void addConferenceTest() throws Exception {
        IConference conference = modelFactory.newConference();
        conference.setId(1);
        conference.setName("Conference1");
        conference.addConference(conferenceDao);
        assertTrue(1 == conference.getId());
        assertTrue("Conference1".equals(conference.getName()));
    }

    @Test
    public void loadDivisionListByConferenceIdTest() throws Exception {
        IConference conference = modelFactory.newConferenceWithId(1);
        IDivisionDao divisionDao = daoFactory.newDivisionDao();
        conference.loadDivisionListByConferenceId(divisionDao);

        assertTrue(conference.getDivisionList().get(0).getId() == (1));
        assertTrue(conference.getDivisionList().get(1).getId() == (2));
        assertTrue(conference.getDivisionList().get(0).getName().equals("Division1"));
        assertNull(conference.getDivisionList().get(1).getName());
    }

}
