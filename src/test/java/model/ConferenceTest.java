package model;

import simulation.data.IAddConferenceFactory;
import simulation.data.ILoadConferenceFactory;
import simulation.data.ILoadDivisionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import simulation.model.Conference;
import simulation.model.Division;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ConferenceTest {

    private static ILoadConferenceFactory loadConferenceFactory;
    private static IAddConferenceFactory addConferenceFactory;

    @BeforeClass
    public static void setFactoryObj(){
        loadConferenceFactory = new LoadConferenceMock();
        addConferenceFactory = new AddConferenceMock();
    }

    @Test
    public void defaultConstructorTest() {
        Conference conference = new Conference();
        assertEquals(conference.getId(), 0);
    }

    @Test
    public void conferenceTest() {
        Conference conference = new Conference(1);
        assertEquals(conference.getId(), 1);
    }

    @Test
    public void conferenceFactoryTest() throws Exception {
        Conference conference = new Conference(1, loadConferenceFactory);
        assertEquals(conference.getId(), 1);
        assertEquals(conference.getName(), "Conference1");

        conference = new Conference(2, loadConferenceFactory);
        assertNull(conference.getName());
    }

    @Test
    public void getLeagueIdTest() throws Exception {
        Conference conference = new Conference(1, loadConferenceFactory);
        assertTrue(conference.getLeagueId() == (1));
    }

    @Test
    public void setLeagueIdTest(){
        Conference conference = new Conference();
        int leagueId = 1;
        conference.setLeagueId( leagueId);
        assertTrue(conference.getLeagueId() == leagueId);
    }

    @Test
    public void getDivisionListTest() throws Exception {
        Conference conference = new Conference(1, loadConferenceFactory);
        List<Division> divisionList = conference.getDivisionList();
        assertNotNull(divisionList);

        assertTrue(divisionList.get(0).getId() == (1));
        assertTrue(divisionList.get(1).getId() == (2));
        assertTrue(divisionList.get(0).getName().equals("Division1"));
        assertNull(divisionList.get(1).getName());
    }

    @Test
    public void setDivisionListTest() throws Exception {
        ILoadDivisionFactory divisionFactory = new LoadDivisionMock();
        List<Division> divisionList = new ArrayList<>();
        Division division = new Division(1, divisionFactory);
        divisionList.add(division);
        division = new Division(2, divisionFactory);
        divisionList.add(division);

        Conference conference = new Conference();
        conference.setDivisionList(divisionList);

        assertTrue(conference.getDivisionList().get(0).getId() == (1));
        assertTrue(conference.getDivisionList().get(1).getId() == (2));
        assertTrue(conference.getDivisionList().get(0).getName().equals("Division1"));
        assertNull(conference.getDivisionList().get(1).getName());
    }

    @Test
    public void addConferenceTest() throws Exception {
        Conference conference = new Conference();
        conference.setId(1);
        conference.setName("Conference1");
        conference.addConference(addConferenceFactory);
        assertTrue(1 == conference.getId());
        assertTrue("Conference1".equals(conference.getName()));
    }

    @Test
    public void loadDivisionListByConferenceIdTest() throws Exception {
        Conference conference = new Conference(1);
        ILoadDivisionFactory loadDivisionFactory = new LoadDivisionMock();
        conference.loadDivisionListByConferenceId(loadDivisionFactory);

        assertTrue(conference.getDivisionList().get(0).getId() == (1));
        assertTrue(conference.getDivisionList().get(1).getId() == (2));
        assertTrue(conference.getDivisionList().get(0).getName().equals("Division1"));
        assertNull(conference.getDivisionList().get(1).getName());
    }

}
