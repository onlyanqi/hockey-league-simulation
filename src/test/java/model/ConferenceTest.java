package model;

import data.IConferenceFactory;
import data.IDivisionFactory;
import data.ITeamFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConferenceTest {

    private static IConferenceFactory factory;

    @BeforeAll
    static void setFactoryObj(){
        factory = new ConferenceMock();
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
    public void conferenceFactoryTest(){
        Conference conference = new Conference(1, factory);
        assertEquals(conference.getId(), 1);
        assertEquals(conference.getName(), "Conference1");

        conference = new Conference(2, factory);
        assertNull(conference.getName());
    }

    @Test
    public void getLeagueIdTest(){
        Conference conference = new Conference(1, factory);
        assertTrue(conference.getLeagueId() == (1));
    }

    @Test
    public void setLeagueIdTest(){
        Conference conference = new Conference();
        long leagueId = 1;
        conference.setLeagueId(leagueId);
        assertTrue(conference.getLeagueId() == leagueId);
    }

    @Test
    public void getDivisionListTest(){
        Conference conference = new Conference(1, factory);
        List<Division> divisionList = conference.getDivisionList();
        assertNotNull(divisionList);

        assertTrue(divisionList.get(0).getId() == (1));
        assertTrue(divisionList.get(1).getId() == (2));
        assertTrue(divisionList.get(0).getName().equals("Division1"));
        assertNull(divisionList.get(1).getName());
    }

    @Test
    public void setDivisionListTest(){
        IDivisionFactory divisionFactory = new DivisionMock();
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


}
